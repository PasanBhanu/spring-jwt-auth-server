package com.softinklab.authentication.service;

import com.softinklab.authentication.config.TokenConfig;
import com.softinklab.authentication.database.model.AutJwtApp;
import com.softinklab.authentication.database.model.AutSession;
import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.database.repository.SessionRepository;
import com.softinklab.authentication.exception.custom.AuthenticationFailedException;
import com.softinklab.authentication.model.UserPrincipal;
import com.softinklab.rest.action.Authentication;
import com.softinklab.rest.exception.DatabaseValidationException;
import com.softinklab.rest.exception.ServiceException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class TokenProviderImpl implements TokenProvider {

    private final SessionRepository sessionRepository;
    private final TokenConfig tokenConfig;

    public TokenProviderImpl(SessionRepository sessionRepository, TokenConfig tokenConfig) {
        this.sessionRepository = sessionRepository;
        this.tokenConfig = tokenConfig;
    }

    @Override
    public String generateJwtToken(AutUser user, AutJwtApp app, Boolean rememberMe) {
        log.info("Generate Token : {}", user);

        Integer tokenValidity = app.getTokenValidity();
        if (rememberMe) {
            tokenValidity *= app.getRememberDays();
        }

        HashMap<String, Object> claims = new HashMap();
        claims.put("user_id", user.getUserId().toString());
        claims.put("username", user.getUsername());
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        claims.put("email_blocked", user.getEmailBlocked());
        claims.put("sms_blocked", user.getSmsBlocked());
        claims.put("roles", user.getRoles());
        claims.put("permissions", user.getPermissions());

        JwtBuilder jwtBuilder = new DefaultJwtBuilder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + tokenValidity));
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setSubject(app.getSubject());
        jwtBuilder.setIssuer(app.getIssuer());
        jwtBuilder.setAudience(app.getAudience());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, this.tokenConfig.getAuthKey().getBytes(StandardCharsets.UTF_8));
        String jwtToken = jwtBuilder.compact();

        log.info("Token Generated : " + jwtToken);

        return jwtToken;
    }

    @Override
    public String cipherToken(String token) {
        try {
            byte[] key = this.tokenConfig.getSecretKey().getBytes(StandardCharsets.UTF_8);
            byte[] nonce = this.tokenConfig.getNonce().getBytes(StandardCharsets.UTF_8);
            byte[] aad = this.tokenConfig.getAad().getBytes(StandardCharsets.UTF_8);

            SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, spec);
            cipher.updateAAD(aad);
            byte[] cipheredToken = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(cipheredToken);
        } catch (Exception ex) {
            log.error("Token Cipher Failed. " + ex.getMessage());
            throw new ServiceException(500, HttpStatus.INTERNAL_SERVER_ERROR, "Token encryption failed.", Collections.singletonList("Token generation failed."));
        }
    }

    @Override
    public String decipherToken(String token) {
        try {
            byte[] key = this.tokenConfig.getSecretKey().getBytes();
            byte[] nonce = this.tokenConfig.getNonce().getBytes(StandardCharsets.UTF_8);
            byte[] aad = this.tokenConfig.getAad().getBytes(StandardCharsets.UTF_8);
            byte[] cipheredToken = DatatypeConverter.parseHexBinary(token);

            SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, spec);
            cipher.updateAAD(aad);
            byte[] decipheredToken = cipher.doFinal(cipheredToken);
            return new String(decipheredToken);
        } catch (Exception ex) {
            log.error("Token Decipher Failed. " + ex.getMessage());
            throw new AuthenticationFailedException(401, HttpStatus.INTERNAL_SERVER_ERROR, "Token decryption failed.");
        }
    }

    @Override
    public String generateSession(AutUser user, AutSession session) {
        UUID uuid = UUID.randomUUID();
        String rememberToken = uuid + ":" + user.getUserId();
        String cipheredRememberToken = cipherToken(rememberToken);
        session.setRememberToken(rememberToken);
        this.sessionRepository.save(session);
        return cipheredRememberToken;
    }

    @Override
    public Jws<Claims> validateToken(String token) {
        log.debug("Attempting to validate token: {}", token);
        try {
            return Jwts.parser().setSigningKey(this.tokenConfig.getAuthKey().getBytes()).parseClaimsJws(token);
        } catch (JwtException ex) {
            log.error("Error parsing JWT token. ", ex.getMessage());
            ArrayList<String> errors = new ArrayList();
            errors.add("JWT token parsing failed");
            throw new AuthenticationFailedException(401, HttpStatus.UNAUTHORIZED, "JWT token parsing failed");
        }
    }

    @Override
    public void forceValidateToken(String token) {

    }

    @Override
    public AutSession validateTokenWithDeviceHashAndUserId(String token, String rememberToken, String deviceHash) {
        Jws<Claims> claims = validateToken(token);
        UserPrincipal user = getUserPrincipalFromClaims(claims);

        String decipherRememberToken = decipherToken(rememberToken);
        String[] tokenContent = decipherRememberToken.split(":");
        if (tokenContent.length != 2){
            throw new AuthenticationFailedException(401, HttpStatus.UNAUTHORIZED, "Remember token not recognised. Invalid refresh attempt.");
        }
        String userIdFromRememberToken = tokenContent[1];

        if (Integer.parseInt(userIdFromRememberToken) != user.getUserId()) {
            throw new AuthenticationFailedException(401, HttpStatus.UNAUTHORIZED, "Remember token not recognised. Invalid login attempt.");
        }
        Optional<AutSession> session = this.sessionRepository.findByUserId_UserIdAndRememberTokenAndTokenAndDeviceHash(user.getUserId(), decipherRememberToken, token, deviceHash);
        if (!session.isPresent()) {
            throw new DatabaseValidationException(401, HttpStatus.UNAUTHORIZED, "Invalid session! Operation cancelled.", Authentication.LOGIN);
        }

        return session.get();
    }

    @Override
    public UserPrincipal getUserPrincipalFromClaims(Jws<Claims> claims) {
        UserPrincipal user = new UserPrincipal();
        user.setUserId(Integer.parseInt((String) claims.getBody().get("user_id")));
        user.setUsername((String) claims.getBody().get("username"));
        user.setFirstName((String) claims.getBody().get("first_name"));
        user.setLastName((String) claims.getBody().get("last_name"));
        user.setEmailBlocked((Boolean) claims.getBody().get("email_blocked"));
        user.setSmsBlocked((Boolean) claims.getBody().get("sms_blocked"));
        String rolesString = (String) claims.getBody().get("roles");
        List<String> roles = rolesString != null ? new ArrayList<>(Arrays.asList(rolesString.split(","))) : new ArrayList<>();
        user.setRoles(roles);
        String permissionsString = (String) claims.getBody().get("permissions");
        List<String> permissions = permissionsString != null ? new ArrayList<>(Arrays.asList(permissionsString.split(","))) : new ArrayList<>();
        user.setPermissions(permissions);
        return user;
    }

}
