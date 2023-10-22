package com.example.organizer_module.model.request;

import com.example.organizer_module.annotation.Duration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class UpdatePassportInfoRequest {

    @NotBlank(message = "Серия и номер паспорта не могут быть пустыми")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min =  10, max = 10, message = "Серия и номер паспорта должны быть 10 символов")
    private String passportNumberAndSerial;

    @NotBlank(message = "Информация о том кем выдан не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size( max = 250, message = "Информация о том кем выдан не может быть больше 250 символов")
    private String passportIssuedWhom;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Duration
    private LocalDate passportDate;


}
