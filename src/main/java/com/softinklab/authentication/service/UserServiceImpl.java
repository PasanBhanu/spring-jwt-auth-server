package com.softinklab.authentication.service;

import com.softinklab.authentication.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{
    @Override
    public UserPrincipal getAuthenticatedUser() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
