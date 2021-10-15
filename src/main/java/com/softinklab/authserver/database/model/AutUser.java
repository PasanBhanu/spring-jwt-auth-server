package com.softinklab.authserver.database.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "AUT_USERS")
public class AutUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Integer userId;

    @Basic
    @Column(name = "USERNAME", length = 320, nullable = false, unique = true)
    private String username;

    @Basic
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Basic
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Basic
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "REGISTERED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registeredAt = new Date();

    @Basic
    @Column(name = "CONFIRMATION_TOKEN", nullable = false)
    private String confirmationToken;

    @Column(name = "EMAIL_CONFIRMED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailConfirmedAt;

    @Basic
    @Column(name = "BLOCKED")
    private Boolean blocked = false;

    @Column(name = "DELETED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Basic
    @Column(name = "DELETED")
    private Boolean deleted = false;

    @Basic
    @Column(name = "EMAIL_BLOCKED")
    private Boolean emailBlocked = false;

    @Basic
    @Column(name = "SMS_BLOCKED")
    private Boolean smsBlocked = false;
}
