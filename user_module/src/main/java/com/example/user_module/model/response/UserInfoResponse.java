package com.example.user_module.model.response;


import com.example.user_module.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInfoResponse {

    @JsonIgnore
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
