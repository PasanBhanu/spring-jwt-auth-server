package com.softinklab.app.database.repository;

import com.softinklab.app.database.model.AutJwtApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtAppRepository extends JpaRepository<AutJwtApp, Integer> {
}
