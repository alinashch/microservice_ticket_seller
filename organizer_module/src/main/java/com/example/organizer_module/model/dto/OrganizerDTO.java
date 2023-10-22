package com.example.organizer_module.model.dto;

import com.example.organizer_module.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizerDTO   implements UserDetails  {

    private Long organizerId;

    private String email;

    private String login;

    private String firstName;

    private String secondName;

    private String surname;

    private String city;

    private String passwordHash;

    private LocalDate dateOfBirth;

    private Boolean isVerified;

    private Boolean isConfirmed;

    private String INN;

    private String passportNumberAndSerial;

    private String passportIssuedWhom;

    private LocalDate passportDate;

    private String address;

    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isVerified;
    }


}
