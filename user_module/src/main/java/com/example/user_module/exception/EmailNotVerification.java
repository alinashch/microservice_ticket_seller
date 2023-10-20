package com.example.user_module.exception;

public class EmailNotVerification extends RuntimeException {

    public EmailNotVerification(String message) {
        super(message);
    }
}