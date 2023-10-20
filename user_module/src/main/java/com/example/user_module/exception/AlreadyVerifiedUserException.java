package com.example.user_module.exception;

public class AlreadyVerifiedUserException extends RuntimeException {

    public AlreadyVerifiedUserException(String message) {
        super(message);
    }
}