package com.softinklab.authserver.service;

import com.softinklab.authserver.database.model.AutSession;
import com.softinklab.authserver.database.model.AutUser;

public interface TokenProvider {
    String generateJwtToken(AutUser user, Boolean rememberMe);

    String cipherToken(String token);

    String generateSession(AutUser user, AutSession session);
}
