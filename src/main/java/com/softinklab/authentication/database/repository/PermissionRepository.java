package com.softinklab.authentication.database.repository;

import com.softinklab.authentication.database.model.AutPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<AutPermission, Integer> {
}
