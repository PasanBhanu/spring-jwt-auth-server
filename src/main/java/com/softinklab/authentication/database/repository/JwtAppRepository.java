package com.softinklab.authentication.database.repository;

import com.softinklab.authentication.database.model.AutJwtApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtAppRepository extends JpaRepository<AutJwtApp, Integer> {
}
