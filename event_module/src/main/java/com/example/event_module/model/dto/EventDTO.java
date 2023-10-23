package com.example.event_module.model.dto;

import com.example.event_module.model.entity.PlaceTime;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventDTO {

    private Long eventId;

    private Long organizerId;

    private String name;

    private String nameGroup;

    private String description;

    private PlaceTime placeTime;

}
