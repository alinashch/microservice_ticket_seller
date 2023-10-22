package com.example.organizer_module.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationEmailDTO {

    private String fullName;
    private String verificationLink;
}