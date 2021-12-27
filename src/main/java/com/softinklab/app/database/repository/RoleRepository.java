package com.softinklab.app.database.repository;

import com.softinklab.app.database.model.AutRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AutRole, Integer> {
}
