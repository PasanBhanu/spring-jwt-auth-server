package com.softinklab.authserver.database.repository;

import com.softinklab.authserver.database.model.AutUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AutUser, Integer> {
    Optional<AutUser> findByUsernameEqualsIgnoreCase(String username);
}
