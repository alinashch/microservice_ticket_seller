package com.example.organizer_module.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizerConfirmResponse {

    @JsonIgnore
    private Long organizerId;

    private String firstName;

    private String secondName;

    private String surname;

    private Boolean isConfirmed;

}
