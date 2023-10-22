package com.example.organizer_module.controller;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@UtilityClass
public class ResponseBuilder {

    public static ResponseEntity<?> build(HttpStatus httpStatus, Map<String, Object> data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> build(HttpStatus httpStatus, Object data) {
        return ResponseEntity.status(httpStatus).body(data);
    }

    public static ResponseEntity<?> buildWithoutBodyResponse(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).build();
    }
}