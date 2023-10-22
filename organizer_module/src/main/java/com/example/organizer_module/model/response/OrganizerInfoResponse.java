package com.example.organizer_module.model.response;


import com.example.organizer_module.model.entity.Role;
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
public class OrganizerInfoResponse {

    @JsonIgnore
    private Long organizerId;

    private String email;

    private String login;

    private String firstName;

    private String secondName;

    private String surname;

    private String city;

    @JsonIgnore
    private String passwordHash;

    private LocalDate dateOfBirth;

    private Boolean isVerified;

    private Boolean isConfirmed;

    @JsonIgnore
    private String INN;

    @JsonIgnore
    private String passportNumberAndSerial;

    @JsonIgnore
    private String passportIssuedWhom;

    @JsonIgnore
    private LocalDate passportDate;

    private String address;

    private Set<Role> roles;

}
