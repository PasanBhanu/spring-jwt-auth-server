package com.softinklab.app.model;

import lombok.Data;

import java.util.List;

@Data
public class UserPrincipal {
    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean emailBlocked;
    private Boolean smsBlocked;
    private List<String> roles;
    private List<String> permissions;
}
