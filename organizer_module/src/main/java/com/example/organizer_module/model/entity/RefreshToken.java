package com.example.organizer_module.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "organizer_refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken  {

    @Id
    private UUID token;

    @Column(nullable = false)
    private Instant validTill;

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;
}
