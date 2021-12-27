package com.softinklab.app.database.repository;

import com.softinklab.app.database.model.AutPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<AutPermission, Integer> {
}
