package com.example.organizer_module.service;

import com.example.organizer_module.exception.*;
import com.example.organizer_module.mapper.OrganizerMapper;
import com.example.organizer_module.mapper.RoleMapper;
import com.example.organizer_module.model.dto.OrganizerDTO;
import com.example.organizer_module.model.entity.Organizer;
import com.example.organizer_module.model.request.SignUpForm;
import com.example.organizer_module.model.request.UpdateEmailRequest;
import com.example.organizer_module.model.request.UpdatePasswordRequest;
import com.example.organizer_module.model.request.UpdatePersonalInfoRequest;
import com.example.organizer_module.model.response.OrganizerInfoResponse;
import com.example.organizer_module.model.response.OrganizerInfoWithPrivateResponse;
import com.example.organizer_module.repository.OrganizerRepository;
import com.example.organizer_module.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizerService {

    private final OrganizerRepository organizerRepository;

    private final OrganizerMapper organizerMapper;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public OrganizerDTO registerUser(SignUpForm request) {
        if (organizerRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("User with this email already exists");
        }
        if (organizerRepository.existsByLogin(request.getLogin())) {
            throw new EntityAlreadyExistsException("User with this login already exists");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        Organizer user = organizerMapper.toEntityFromRequest(request, Set.of(roleService.getUserRole()), BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        user = organizerRepository.save(user);
        return organizerMapper.toDTOFromEntity(user);
    }

    @Transactional
    public void verifyUserById(Long id) {
        Organizer organizer=organizerRepository.findById(id).get();
        organizer.setRoles(roleMapper.toEntityFromDTOSet(Set.of(roleService.getOrganizerRole())));
        organizerRepository.save(organizer);
        organizerRepository.verifyUserById(id);
    }

    @Transactional
    public OrganizerInfoResponse getUserInfo(OrganizerDTO organizerDTO) {
        if (!organizerDTO.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if (refreshTokenRepository.getAllByUser_UserId(organizerDTO.getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid ");
        }

        return organizerMapper.toUserInfoResponseFromUserDTO(organizerDTO);
    }

    @Transactional
    public OrganizerInfoWithPrivateResponse getUserInfoWithPrivate(OrganizerDTO organizerDTO) {
        if (!organizerDTO.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if (refreshTokenRepository.getAllByUser_UserId(organizerDTO.getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid ");
        }

        return organizerMapper.toUserInfoWithPrivateResponseFromUserDTO(organizerDTO);
    }

    @Transactional
    public void updateProfile(OrganizerDTO organizerDTO, UpdatePersonalInfoRequest request) {
        if (refreshTokenRepository.getAllByUser_UserId(organizerDTO.getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        Organizer entity = organizerMapper.toEntityFromDTO(organizerDTO);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        organizerMapper.updateEntity(request, entity);
        organizerRepository.save(entity);
    }

    @Transactional
    public void updatePassword(OrganizerDTO organizerDTO, UpdatePasswordRequest request) {
        if (refreshTokenRepository.getAllByUser_UserId(organizerDTO.getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        Organizer entity = organizerMapper.toEntityFromDTO(organizerDTO);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        entity.setPasswordHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        organizerMapper.updateEntity(request, entity);
        organizerRepository.save(entity);
    }

    @Transactional
    public OrganizerDTO updateEmail(OrganizerDTO organizerDTO, UpdateEmailRequest request) {
        if (refreshTokenRepository.getAllByUser_UserId(organizerDTO.getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        Organizer entity = organizerMapper.toEntityFromDTO(organizerDTO);
        entity.setIsVerified(false);
        if (organizerRepository.getByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("USer with this email already exists");
        }

        organizerMapper.updateEntity(request, entity);
        return organizerMapper.toDTOFromEntity(organizerRepository.save(entity));
    }

    @Transactional
    public void deleteOrganizer(Long id) {
        if (refreshTokenRepository.getAllByUser_UserId(id) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        Organizer entity = organizerRepository.findById(id).get();
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        organizerRepository.deleteToken(id);
        organizerRepository.delete(organizerRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this ID")
        ));
    }

    @Transactional
    public void deleteSession(String login) {
        if (refreshTokenRepository.getAllByUser_UserId(organizerRepository.getByLogin(login).get().getOrganizerId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        organizerRepository.deleteToken(organizerRepository.getByLogin(login).get().getOrganizerId());
    }

    public OrganizerDTO getOrganizerByEmail(String email) {
        Organizer user = organizerRepository.getByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find organizer with this Email")
        );
        return organizerMapper.toDTOFromEntity(user);
    }

    public OrganizerDTO getOrganizerById(Long id) {
        Organizer user = organizerRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find organizer with this ID")
        );
        return organizerMapper.toDTOFromEntity(user);
    }

    public OrganizerDTO getOrganizerByLogin(String login) {
        Organizer user = organizerRepository.getByLogin(login).orElseThrow(
                () -> new EntityDoesNotExistException("The organizer with this login does not exist")
        );
        return organizerMapper.toDTOFromEntity(user);
    }

}
