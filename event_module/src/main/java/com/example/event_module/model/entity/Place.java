package com.example.event_module.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "place_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;

    @Column( nullable = false)
    private String address;

    @Column( nullable = false)
    private int capacity;

    @Column( nullable = false)
    private String city;

}
