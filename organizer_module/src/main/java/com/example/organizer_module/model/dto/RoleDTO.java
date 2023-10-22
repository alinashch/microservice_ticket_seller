package com.example.organizer_module.model.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleDTO implements GrantedAuthority {

    private Long roleId;
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}