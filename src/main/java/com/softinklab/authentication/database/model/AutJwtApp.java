package com.softinklab.authentication.database.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUT_JWT_APPS")
public class AutJwtApp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "APP_ID")
    private Integer appId;

    @Basic
    @Column(name = "APP_NAME")
    private String appName;

    @Basic
    @Column(name = "TOKEN_VALIDITY")
    private Integer tokenValidity = 86400;

    @Basic
    @Column(name = "REMEMBER_DAYS")
    private Integer rememberDays = 30;

    @Basic
    @Column(name = "AUDIENCE")
    private String audience;

    @Basic
    @Column(name = "SUBJECT")
    private String subject;

    @Basic
    @Column(name = "ISSUER")
    private String issuer;
}
