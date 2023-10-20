package com.example.user_module.service;

import com.example.user_module.exception.*;
import com.example.user_module.mapper.VerificationMapper;
import com.example.user_module.model.dto.UserDTO;
import com.example.user_module.model.entity.*;

import com.example.user_module.repository.VerificationRepository;
import lombok.AllArgsConstructor;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
@Service
@AllArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;

    private final VerificationMapper verificationMapper;

    @Transactional
    public UUID createCodeAndSave(UserDTO user) {
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
        return verification.getUser().getUserId();
    }

    public UUID resendCode(UserDTO user) {
        if (user.getIsVerified()) {
            throw new AlreadyVerifiedUserException("The user has already been verified");
        }
        return createCodeAndSave(user);
    }
}