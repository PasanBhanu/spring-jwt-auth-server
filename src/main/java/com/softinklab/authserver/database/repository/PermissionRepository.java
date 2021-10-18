package com.softinklab.authserver.database.repository;

import com.softinklab.authserver.database.model.AutPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<AutPermission, Integer> {
}
