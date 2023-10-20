package com.example.user_module.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {

    @NotBlank
    @Size(max = 100)
    private String login;

    @NotBlank
    @Size(min = 8, max = 50)
    private String password;
}