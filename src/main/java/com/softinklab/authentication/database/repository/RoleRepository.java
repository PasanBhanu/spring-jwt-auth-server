package com.softinklab.authentication.database.repository;

import com.softinklab.authentication.database.model.AutRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AutRole, Integer> {
}
