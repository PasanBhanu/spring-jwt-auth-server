package com.softinklab.authentication.database.repository;

import com.softinklab.authentication.database.model.AutSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<AutSession, Long> {
    Optional<AutSession> findByUserId_UserIdAndRememberTokenAndTokenAndDeviceHash(Integer userId, String rememberToken, String token, String deviceHash);
}
