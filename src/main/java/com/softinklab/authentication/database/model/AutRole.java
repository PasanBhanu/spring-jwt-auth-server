package com.softinklab.authentication.database.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUT_ROLES")
public class AutRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_ID")
    private Integer roleId;

    @Basic
    @Column(name = "ROLE_NAME")
    private String roleName;

    @Basic
    @Column(name = "GUARD")
    private String guard;
}
