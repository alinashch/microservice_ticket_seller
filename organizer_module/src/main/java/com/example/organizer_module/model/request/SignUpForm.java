package com.example.organizer_module.model.request;

import com.example.organizer_module.annotation.Duration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpForm {

    @NotBlank(message = "Логин не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Логин не может быть меньше 8 и больше 20 символов")
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String password;

    @Email(message = "Почта указана неверно")
    @NotBlank(message = "Почта не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Имя не может быть больше 100 символов")
    private String firstName;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Отчество не может быть больше 100 символов")
    private String secondName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Фамилия не может быть больше 100 символов")
    private String surname;

    @NotBlank(message = "Город не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 100, message = "Город не может быть больше 100 символов")
    private String city;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @Duration
    private LocalDate dateOfBirth;

    @NotBlank(message = "Пароль не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 8, max = 20, message = "Пароль не может быть меньше 8 и больше 20 символов")
    private String repeatPassword;

    @NotBlank(message = "Инн не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 12, max = 12, message = "Инн должен быть 12 символов")
    private String INN;

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

    @NotBlank(message = "Адрес не может быть пустой")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size( max = 250, message = "Адрес не может быть больше 250 символов")
    private String address;
}