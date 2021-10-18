package com.softinklab.authserver.service;

import com.softinklab.authserver.config.TokenConfig;
import com.softinklab.authserver.database.model.AutSession;
import com.softinklab.authserver.database.model.AutUser;
import com.softinklab.authserver.database.repository.SessionRepository;
import com.softinklab.authserver.exception.custom.ServiceException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Slf4j
@Service
public class TokenProviderImpl implements TokenProvider {

    private final SessionRepository sessionRepository;
    private final TokenConfig tokenConfig;
    private final SecureRandom secureRandom = new SecureRandom();

    public TokenProviderImpl(SessionRepository sessionRepository, TokenConfig tokenConfig) {
        this.sessionRepository = sessionRepository;
        this.tokenConfig = tokenConfig;
    }

    public String generateJwtToken(AutUser user, Boolean rememberMe) {
        log.info("Generate Token : {}", user);

        Long tokenValidity = this.tokenConfig.getTokenValidity();
        if (rememberMe) {
            tokenValidity *= this.tokenConfig.getRememberDays();
        }

        HashMap<String, Object> claims = new HashMap();
        claims.put("user_id", user.getUserId().toString());
        claims.put("username", user.getUsername());
        claims.put("first_name", user.getFirstName());
        claims.put("last_name", user.getLastName());
        claims.put("roles", user.getRoles());
        claims.put("permissions", user.getPermissions());

        JwtBuilder jwtBuilder = new DefaultJwtBuilder();
        jwtBuilder.setClaims(claims);
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + tokenValidity));
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setSubject(this.tokenConfig.getSubject());
        jwtBuilder.setIssuer(this.tokenConfig.getIssuer());
        jwtBuilder.setAudience(this.tokenConfig.getAudience());
        jwtBuilder.signWith(SignatureAlgorithm.HS256, this.tokenConfig.getAuthKey().getBytes(StandardCharsets.UTF_8));
        String jwtToken = jwtBuilder.compact();

        log.info("Token Generated : " + jwtToken);

        return jwtToken;
    }

    public String cipherToken(String token) {
        try {
            byte[] key = this.tokenConfig.getSecretKey().getBytes();
            byte[] nonce = new byte[12];
            this.secureRandom.nextBytes(nonce);
            SecretKey aesKey = new SecretKeySpec(key, 0, key.length, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec spec = new GCMParameterSpec(128, nonce);
            cipher.init(1, aesKey, spec);
            byte[] aad = new byte[32];
            this.secureRandom.nextBytes(aad);
            cipher.updateAAD(aad);
            byte[] cipheredToken = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(cipheredToken);
        } catch (Exception ex) {
            log.error("Token Cipher Failed. " + ex.getMessage());
            ArrayList<String> errors = new ArrayList();
            errors.add("Token generation failed");
            throw new ServiceException(500, HttpStatus.INTERNAL_SERVER_ERROR, "Token encryption failed", errors);
        }
    }

    public String generateSession(AutUser user, AutSession session) {
        UUID uuid = UUID.randomUUID();
        String rememberToken = uuid + ":" + user.getUserId();
        String cipheredRememberToken = cipherToken(rememberToken);
        session.setRememberToken(cipheredRememberToken);
        this.sessionRepository.save(session);
        return cipheredRememberToken;
    }
}
