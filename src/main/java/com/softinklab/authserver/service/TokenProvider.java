package com.softinklab.authserver.service;

import com.softinklab.authserver.database.model.AutSession;
import com.softinklab.authserver.database.model.AutUser;
import com.softinklab.authserver.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenProvider {
    String generateJwtToken(AutUser user, Boolean rememberMe);

    String cipherToken(String token);

    String generateSession(AutUser user, AutSession session);

    Jws<Claims> validateToken(String token);

    UserPrincipal getUserPrincipalFromClaims(Jws<Claims> claims);

    Boolean forceValidateToken(String token);
}
