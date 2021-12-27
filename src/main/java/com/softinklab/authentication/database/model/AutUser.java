package com.softinklab.authentication.database.model;

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
    @Column(name = "PASSWORD_RESET_TOKEN")
    private String passwordResetToken;

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

    @Basic
    @Column(name = "ROLES")
    private String roles;

    @Basic
    @Column(name = "PERMISSIONS", length = 1024)
    private String permissions;

    @Basic
    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Basic
    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Basic
    @Column(name = "CITY")
    private String city;

    @Basic
    @Column(name = "STATE")
    private String state;

    @Basic
    @Column(name = "COUNTRY", length = 100)
    private String country;

    @Basic
    @Column(name = "POSTAL_CODE", length = 50)
    private String postalCode;

    @Basic
    @Column(name = "MOBILE_NO", length = 20)
    private String mobileNo;

    @Basic
    @Column(name = "SEX", length = 10)
    private String sex;

    @Basic
    @Column(name = "PREFIX", length = 10)
    private String prefix;

    @Basic
    @Column(name = "VAT_NO", length = 50)
    private String vatNo;

    @Basic
    @Column(name = "NIC_NO", length = 20)
    private String nicNo;

    @Basic
    @Column(name = "COMPANY_NAME")
    private String companyName;

}
