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
}
