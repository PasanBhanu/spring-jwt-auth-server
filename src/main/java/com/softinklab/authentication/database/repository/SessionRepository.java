package com.softinklab.authentication.database.repository;

import com.softinklab.authentication.database.model.AutSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<AutSession, Long> {
}
