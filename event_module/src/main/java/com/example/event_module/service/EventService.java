package com.example.event_module.service;


import com.example.event_module.model.dto.EventDTO;
import com.example.event_module.model.entity.Event;
import com.example.event_module.model.mapper.EventMapper;
import com.example.event_module.model.request.EventCreateRequest;
import com.example.event_module.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final EventMapper eventMapper;

    @Transactional
    public EventDTO createEvent(EventCreateRequest eventCreateRequest) {
        Event event = eventRepository.save(eventMapper.toEventFromCreateRequest(eventCreateRequest));
        return eventMapper.toDTOFromEvent(event);
    }

    public List<Event> findByName() {
        return new ArrayList<>();
    }

    public EventDTO findById() {
        return new EventDTO();
    }

    public List<Event> findByNameGroup() {
        return new ArrayList<>();

    }

    public List<Event> findByPlace() {
        return new ArrayList<>();

    }

}
