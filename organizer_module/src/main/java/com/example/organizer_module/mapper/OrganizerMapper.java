package com.example.organizer_module.mapper;

import com.example.organizer_module.model.dto.OrganizerDTO;
import com.example.organizer_module.model.dto.RoleDTO;
import com.example.organizer_module.model.entity.Organizer;
import com.example.organizer_module.model.request.SignUpForm;
import com.example.organizer_module.model.request.UpdateEmailRequest;
import com.example.organizer_module.model.request.UpdatePasswordRequest;
import com.example.organizer_module.model.request.UpdatePersonalInfoRequest;
import com.example.organizer_module.model.response.OrganizerInfoResponse;
import com.example.organizer_module.model.response.OrganizerInfoWithPrivateResponse;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(uses = {RoleMapper.class})
public interface OrganizerMapper {

    OrganizerMapper INSTANCE = Mappers.getMapper(OrganizerMapper.class);

    OrganizerDTO toDTOFromEntity(Organizer entity);

    Organizer toEntityFromDTO(OrganizerDTO dto);

    OrganizerInfoResponse toUserInfoResponseFromUserDTO(OrganizerDTO dto);

    OrganizerInfoWithPrivateResponse toUserInfoWithPrivateResponseFromUserDTO(OrganizerDTO dto);

    @Mapping(target = "isVerified", expression = "java(false)")
    @Mapping(target = "isConfirmed", expression = "java(false)")
    @Mapping(target = "dateOfBirth" , source = "request.dateOfBirth")
    Organizer toEntityFromRequest(SignUpForm request, Set<RoleDTO> roles, String passwordHash);

    @Mapping(target = "isVerified", expression = "java(false)")
    Organizer toEntityFromUpdateEmailInfoForm(UpdateEmailRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdatePersonalInfoRequest request, @MappingTarget Organizer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdatePasswordRequest request, @MappingTarget Organizer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateEmailRequest request, @MappingTarget Organizer entity);

}
