package com.example.user_module.controller;


import com.example.user_module.model.dto.*;
import com.example.user_module.model.request.*;
import com.example.user_module.service.AuthenticationService;
import com.example.user_module.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.example.user_module.model.constant.Role.USER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final AuthenticationService authService;

    @GetMapping("/credentials")
    @Operation(summary = "Получение информации о пользователе", tags = USER, responses = {
            @ApiResponse(responseCode = "200", description = "Получение данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CredentialsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getCredentials(Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, userService.getCredentials(user));
    }

    @PutMapping("/profile/update/personal-information")
    @Operation(summary = "Обновление информации о пользователе", tags = USER, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "409",
                    description = "Пользователь c указанным ником уже существует",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExceptionDTO.class))
                    })
    })
    public ResponseEntity<?> updateProfileInformation(@RequestBody @Valid UpdateProfileRequest request,
                                                      Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.updateProfile(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/password")
    @Operation(summary = "Обновление информации о пароле", tags = USER, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> updateProfilePassword(@RequestBody @Valid UpdateProfilePassword request,
                                                   Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.updatePassword(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/email")
    @Operation(summary = "Обновление информации о email", tags = USER, responses = {
            @ApiResponse(responseCode = "200", description = "Информация обновлена", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> updateProfileEmail(@RequestBody @Valid UpdateEmailInfoForm request,
                                                Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        authService.resendCode( request, user);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Удаление пользователя", tags = USER, responses = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален", content = {
                    @Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "Пользователя не существует", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> deleteUser( Authentication authentication) {
        UserDTO user = userService.getUserByLogin((String) authentication.getPrincipal());
        userService.deleteUser(user.getUserId());
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

}