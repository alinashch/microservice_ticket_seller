package com.example.organizer_module.mapper;

import com.example.organizer_module.model.dto.OrganizerDTO;
import com.example.organizer_module.model.entity.Verification;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.Instant;
import java.util.UUID;

@Mapper(uses = OrganizerMapper.class)
public interface VerificationMapper {

    VerificationMapper INSTANCE = Mappers.getMapper(VerificationMapper.class);

    Verification toEntityFromParams(OrganizerDTO organizerDTO, Instant validTill, UUID code);
}