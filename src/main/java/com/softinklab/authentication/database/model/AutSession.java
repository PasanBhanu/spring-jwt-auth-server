package com.softinklab.authentication.database.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "AUT_SESSIONS")
public class AutSession {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "SESSION_USER_ID")
    private AutUser userId;

    @Basic
    @Column(name = "TOKEN", length = 2048)
    private String token;

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOGGED_AT")
    private Date loggedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "SESSION_APP_ID")
    private AutJwtApp appId;

    @Basic
    @Column(name = "REMEMBER_ME")
    private Boolean rememberMe;

    @Basic
    @Column(name = "REMEMBER_TOKEN")
    private String rememberToken;

    @Basic
    @Column(name = "BROWSER")
    private String browser;

    @Basic
    @Column(name = "OS")
    private String os;

    @Basic
    @Column(name = "DEVICE_NAME")
    private String deviceName;

    @Basic
    @Column(name = "DEVICE_HASH")
    private String deviceHash;
}
