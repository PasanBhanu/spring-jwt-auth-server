package com.softinklab.authentication.service;

import com.softinklab.authentication.model.UserPrincipal;

public interface UserService {
    UserPrincipal getAuthenticatedUser();
}
