package com.softinklab.authserver.database.repository;

import com.softinklab.authserver.database.model.AutSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<AutSession, Long> {
}
