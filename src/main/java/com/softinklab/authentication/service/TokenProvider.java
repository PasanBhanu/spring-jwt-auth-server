package com.softinklab.authentication.service;

import com.softinklab.authentication.database.model.AutJwtApp;
import com.softinklab.authentication.database.model.AutSession;
import com.softinklab.authentication.database.model.AutUser;
import com.softinklab.authentication.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenProvider {
    String generateJwtToken(AutUser user, AutJwtApp app, Boolean rememberMe);

    String cipherToken(String token);

    String generateSession(AutUser user, AutSession session);

    Jws<Claims> validateToken(String token);

    UserPrincipal getUserPrincipalFromClaims(Jws<Claims> claims);

    void forceValidateToken(String token);

    AutSession validateTokenWithDeviceHashAndUserId(String token, String rememberToken, String deviceHash);

    String decipherToken(String token);
}
