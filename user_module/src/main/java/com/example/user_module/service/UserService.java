package com.example.user_module.service;

import com.example.user_module.exception.*;
import com.example.user_module.mapper.RoleMapper;
import com.example.user_module.mapper.UserMapper;
import com.example.user_module.model.dto.CredentialsDTO;
import com.example.user_module.model.dto.UserDTO;
import com.example.user_module.model.entity.*;
import com.example.user_module.model.request.SignUpForm;
import com.example.user_module.model.request.UpdateEmailInfoForm;
import com.example.user_module.model.request.UpdateProfilePassword;
import com.example.user_module.model.request.UpdateProfileRequest;
import com.example.user_module.model.response.UserInfoResponse;
import com.example.user_module.model.response.UserInfoWithPrivateResponse;
import com.example.user_module.repository.RefreshTokenRepository;
import com.example.user_module.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    private final RefreshTokenRepository refreshTokenRepository;


    @Transactional
    public UserDTO registerUser(SignUpForm request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityAlreadyExistsException("User with this email already exists");
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new EntityAlreadyExistsException("User with this login already exists");
        }

        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        User user = userMapper.toEntityFromRequest(request, Set.of(roleService.getUserRole()), BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        user = userRepository.save(user);
        return userMapper.toDTOFromEntity(user);
    }

    @Transactional
    public void verifyUserById(Long id) {
        User user=userRepository.findById(id).get();
        user.setRoles(roleMapper.toEntityFromDTOSet(Set.of(roleService.getBuyerRole())));
        userRepository.save(user);
        userRepository.verifyUserById(id);
    }

    @Transactional
    public UserInfoResponse getUserInfo(UserDTO user) {
        if (!user.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid ");
        }

        return userMapper.toUserInfoResponseFromUserDTO(user);
    }

    @Transactional
    public UserInfoWithPrivateResponse getUserInfoWithPrivate(UserDTO user) {
        if (!user.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid ");
        }

        return userMapper.toUserInfoWithPrivateResponseFromUserDTO(user);
    }

    @Transactional
    public void updateProfile(UserDTO user, UpdateProfileRequest request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }

        User entity = userMapper.toEntityFromDTO(user);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }

        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public void updatePassword(UserDTO user, UpdateProfilePassword request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new PasswordDoesNotMatchException("Password does not match");
        }
        User entity = userMapper.toEntityFromDTO(user);
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }

        entity.setPasswordHash(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        userMapper.updateEntity(request, entity);
        userRepository.save(entity);
    }

    @Transactional
    public UserDTO updateEmail(UserDTO user, UpdateEmailInfoForm request) {
        if (refreshTokenRepository.getAllByUser_UserId(user.getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userMapper.toEntityFromDTO(user);
        entity.setIsVerified(false);
        if (userRepository.getByEmail(request.getEmail()).isPresent()) {
            throw new EntityAlreadyExistsException("USer with this email already exists");
        }

        userMapper.updateEntity(request, entity);
        return userMapper.toDTOFromEntity(userRepository.save(entity));
    }

    @Transactional
    public void deleteUser(Long userId) {
        if (refreshTokenRepository.getAllByUser_UserId(userId) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userRepository.findById(userId).get();
        if (!entity.getIsVerified()) {
            throw new EmailNotVerification("The email is not verification ");
        }
        userRepository.deleteToken(userId);
        userRepository.delete(userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this ID")
        ));
    }

    @Transactional
    public void deleteSession(String login) {
        if (refreshTokenRepository.getAllByUser_UserId(userRepository.getByLogin(login).get().getUserId()) == 0) {
            throw new TokenExpiredException("The token is not valid");
        }
        User entity = userRepository.getByLogin(login).get();

        userRepository.deleteToken(userRepository.getByLogin(login).get().getUserId());
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.getByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this Email")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityDoesNotExistException("Can not find user with this ID")
        );
        return userMapper.toDTOFromEntity(user);
    }

    public UserDTO getUserByLogin(String login) {

        User user = userRepository.getByLogin(login).orElseThrow(
                () -> new EntityDoesNotExistException("The user with this login does not exist")
        );
        return userMapper.toDTOFromEntity(user);
    }

}
