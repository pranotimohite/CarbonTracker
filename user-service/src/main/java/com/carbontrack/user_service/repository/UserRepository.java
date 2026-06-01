package com.carbontrack.user_service.repository;

import com.carbontrack.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByAuthId(Long authId);
    Optional<User> findByAuthId(Long authId);
}