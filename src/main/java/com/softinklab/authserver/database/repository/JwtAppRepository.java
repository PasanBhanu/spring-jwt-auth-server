package com.softinklab.authserver.database.repository;

import com.softinklab.authserver.database.model.AutJwtApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtAppRepository extends JpaRepository<AutJwtApp, Integer> {
}
