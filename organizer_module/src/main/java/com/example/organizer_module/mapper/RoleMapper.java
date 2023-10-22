package com.example.organizer_module.mapper;


import com.example.organizer_module.model.dto.RoleDTO;

import com.example.organizer_module.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDTO toDTOFromEntity(Role entity);

    Role toEntityFromDTO(RoleDTO entity);


    Set<RoleDTO> toDTOFromEntitySet(Set<Role> entities);

    Set<Role> toEntityFromDTOSet(Set<RoleDTO> dtos);
}