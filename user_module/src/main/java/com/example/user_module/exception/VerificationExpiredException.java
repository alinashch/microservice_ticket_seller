package com.example.user_module.exception;

public class VerificationExpiredException extends RuntimeException {

    public VerificationExpiredException(String message) {
        super(message);
    }
}
