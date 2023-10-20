package com.example.user_module.model.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEmailInfoForm {

    @NotBlank
    @Size(max = 255)
    @Email
    private String email;
}

