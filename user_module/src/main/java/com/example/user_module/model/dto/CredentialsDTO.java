package com.example.user_module.model.dto;

import com.example.user_module.model.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class CredentialsDTO {

    private Long userId;

    private String email;

    private String login;

    private String firstName;

    private String surname;

    private String secondName;

    @JsonIgnore
    private String passwordHash;

    private LocalDate dateOfBirth;

    private Boolean isVerified;

    private Set<Role> roles ;
}