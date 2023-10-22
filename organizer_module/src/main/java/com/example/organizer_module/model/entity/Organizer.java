package com.example.organizer_module.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "organizer_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Organizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long organizerId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String secondName;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @Column(nullable = false, unique = true)
    private String INN;

    @Column(nullable = false, unique = true)
    private String passportNumberAndSerial;

    @Column(nullable = false)
    private String passportIssuedWhom;

    @Column(nullable = false)
    private LocalDate passportDate;

    @Column(nullable = false)
    private String address;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "organizer_role",
            joinColumns = {@JoinColumn(name = "organizer_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

}
