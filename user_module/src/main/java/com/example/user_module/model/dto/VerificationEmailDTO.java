package com.example.user_module.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerificationEmailDTO {

    private String fullName;
    private String verificationLink;
}