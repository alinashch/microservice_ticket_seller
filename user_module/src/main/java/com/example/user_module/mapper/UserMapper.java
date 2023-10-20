package com.example.user_module.mapper;

import com.example.user_module.model.dto.CredentialsDTO;
import com.example.user_module.model.dto.RoleDTO;
import com.example.user_module.model.dto.UserDTO;
import com.example.user_module.model.entity.*;
import com.example.user_module.model.request.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(uses = {RoleMapper.class})
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTOFromEntity(User entity);

    User toEntityFromDTO(UserDTO dto);

    UserRequest toRequestFromEntity(User user);

    CredentialsDTO toCredentialsDTOFromDTO(UserDTO dto);

    @Mapping(target = "isVerified", expression = "java(false)")
    @Mapping(target = "dateOfBirth" , source = "request.dateOfBirth")
    User toEntityFromRequest(SignUpForm request, Set<RoleDTO> roles, String passwordHash);

    @Mapping(target = "isVerified", expression = "java(false)")
    User toEntityFromUpdateEmailInfoForm(UpdateEmailInfoForm request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfileRequest request, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateProfilePassword request, @MappingTarget User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateEmailInfoForm request, @MappingTarget User entity);

}
