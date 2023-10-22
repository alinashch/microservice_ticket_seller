package com.example.organizer_module.exception;

public class WrongInputLoginException extends RuntimeException {

    public WrongInputLoginException(String message) {
        super(message);
    }
}
