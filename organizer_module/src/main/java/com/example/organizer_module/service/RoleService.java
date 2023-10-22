package com.example.organizer_module.service;

import com.example.organizer_module.exception.EntityDoesNotExistException;
import com.example.organizer_module.mapper.RoleMapper;
import com.example.organizer_module.model.dto.RoleDTO;
import com.example.organizer_module.model.entity.Role;
import com.example.organizer_module.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.organizer_module.model.constant.Role.ORGANIZER;
import static com.example.organizer_module.model.constant.Role.USER;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    public RoleDTO getUserRole() {
        Role role = roleRepository.findByName(USER).orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return roleMapper.toDTOFromEntity(role);
    }

    public RoleDTO getOrganizerRole() {
        Role role = roleRepository.findByName(ORGANIZER).orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return roleMapper.toDTOFromEntity(role);
    }

}