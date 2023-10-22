package com.example.organizer_module.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokensDTO {

    private String accessToken;
    private String refreshToken;
}