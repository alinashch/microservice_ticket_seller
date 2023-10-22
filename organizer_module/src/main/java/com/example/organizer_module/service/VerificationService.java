package com.example.organizer_module.service;

import com.example.organizer_module.exception.*;
import com.example.organizer_module.mapper.VerificationMapper;
import com.example.organizer_module.model.dto.*;
import com.example.organizer_module.model.entity.Verification;
import com.example.organizer_module.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
@Service
@AllArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;

    private final VerificationMapper verificationMapper;

    @Transactional
    public UUID createCodeAndSave(OrganizerDTO user) {
        UUID code = UUID.randomUUID();
        verificationRepository.save(
                verificationMapper.toEntityFromParams(user, Instant.now().plusSeconds(3600), code));
        return code;
    }


    @Transactional
    public Long getVerificationUserId(String code) {
        Verification verification = verificationRepository.findByCode(UUID.fromString(code)).orElseThrow(
                () -> new EntityDoesNotExistException("This verification code does not exist")
        );
        if (verification.getValidTill().isBefore(Instant.now())) {
            throw new VerificationExpiredException("The code expired");
        }
        verificationRepository.delete(verification);
        return verification.getOrganizer().getOrganizerId();
    }

    public UUID resendCode(OrganizerDTO user) {
        if (user.getIsVerified()) {
            throw new AlreadyVerifiedUserException("The user has already been verified");
        }
        return createCodeAndSave(user);
    }
}