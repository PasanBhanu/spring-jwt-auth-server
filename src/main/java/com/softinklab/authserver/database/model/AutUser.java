package com.softinklab.authserver.database.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUT_USERS")
public class AutUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer userId;

    @Basic
    @Column(name = "USERNAME")
    private String username;

    @Basic
    @Column (name = "PASSWORD")
    private String password;
}
