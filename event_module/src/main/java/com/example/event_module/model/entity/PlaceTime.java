package com.example.event_module.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "place_time_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaceTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeTimeId;

    @Column(nullable = false)
    private Instant startTime;

    @Column(nullable = false)
    private Instant endTime;

    @Column(nullable = false)
    private Boolean isFree;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

}
