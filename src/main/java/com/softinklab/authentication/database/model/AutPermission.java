package com.softinklab.authentication.database.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "AUT_PERMISSIONS")
public class AutPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERMISSION_ID")
    private Integer permissionId;

    @Basic
    @Column(name = "PERMISSION_NAME")
    private String permissionName;

    @Basic
    @Column(name = "GUARD")
    private String guard;
}
