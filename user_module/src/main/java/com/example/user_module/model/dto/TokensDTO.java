package com.example.user_module.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class TokensDTO {

    private String accessToken;
    private String refreshToken;
}