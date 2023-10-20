package com.example.user_module.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String secondName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String surname;


}