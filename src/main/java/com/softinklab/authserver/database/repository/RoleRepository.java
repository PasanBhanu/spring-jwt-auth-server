package com.softinklab.authserver.database.repository;

import com.softinklab.authserver.database.model.AutRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AutRole, Integer> {
}
