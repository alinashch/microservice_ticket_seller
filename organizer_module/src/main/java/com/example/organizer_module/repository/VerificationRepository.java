package com.example.organizer_module.repository;

import com.example.organizer_module.model.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VerificationRepository extends JpaRepository<Verification, UUID> {

    Optional<Verification> findByCode(UUID code);
}