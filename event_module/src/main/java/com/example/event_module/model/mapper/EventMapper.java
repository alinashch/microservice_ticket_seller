package com.example.event_module.model.mapper;

import com.example.event_module.model.dto.EventDTO;
import com.example.event_module.model.entity.Event;
import com.example.event_module.model.request.EventCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    Event toEventFromCreateRequest(EventCreateRequest eventCreateRequest);

    Event toEventFromDTO(EventDTO eventCreateRequest);

    EventDTO toDTOFromEvent(Event eventCreateRequest);

}
