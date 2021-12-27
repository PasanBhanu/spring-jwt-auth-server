package com.softinklab.app.service;

import com.softinklab.app.database.model.AutJwtApp;
import com.softinklab.app.database.model.AutSession;
import com.softinklab.app.database.model.AutUser;
import com.softinklab.app.model.UserPrincipal;
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
