package com.example.user_module.service;

import com.example.user_module.exception.EntityDoesNotExistException;
import com.example.user_module.mapper.RoleMapper;
import com.example.user_module.model.dto.RoleDTO;
import com.example.user_module.model.entity.Role;
import com.example.user_module.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.user_module.model.constant.Role.BUYER;
import static com.example.user_module.model.constant.Role.USER;


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

    public RoleDTO getBuyerRole() {
        Role role = roleRepository.findByName(BUYER).orElseThrow(
                () -> new EntityDoesNotExistException("Роль с данным именем не существует")
        );
        return roleMapper.toDTOFromEntity(role);
    }

}