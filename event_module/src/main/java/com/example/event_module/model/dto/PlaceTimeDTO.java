package com.example.event_module.model.dto;

import com.example.event_module.model.entity.Place;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlaceTimeDTO {

    private Long placeTimeId;

    private Instant startTime;

    private Instant endTime;

    private Boolean isFree;

    private Place place;

}
