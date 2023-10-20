package com.example.user_module.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshTokenEntity  {

    @Id
    private UUID token;

    @Column(nullable = false)
    private Instant validTill;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
