package com.example.user_module.exception;

public class PasswordDoesNotMatchException extends RuntimeException {

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}