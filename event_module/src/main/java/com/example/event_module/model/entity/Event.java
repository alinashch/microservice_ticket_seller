package com.example.event_module.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false)
    private Long organizerId;

    @Column( nullable = false)
    private String name;

    @Column(nullable = false)
    private String nameGroup;

    @Column(nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    private PlaceTime placeTime;

}
