package com.example.organizer_module.controller;


import com.example.organizer_module.model.dto.ExceptionDTO;
import com.example.organizer_module.model.dto.OrganizerDTO;
import com.example.organizer_module.model.request.*;
import com.example.organizer_module.service.AuthenticationService;
import com.example.organizer_module.service.OrganizerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.example.organizer_module.model.constant.Tag.ORGANIZER;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/organizer")
public class OrganizerController {

    private final OrganizerService organizerService;

    private final AuthenticationService authService;

    @GetMapping("/user-info")
    @Operation(summary = "Получение информации о пользователе", tags = ORGANIZER, responses = {
            @ApiResponse(responseCode = "200", description = "Получение данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getUserInfo(Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, organizerService.getUserInfo(user));
    }

    @GetMapping("/check-confirm")
    @Operation(summary = "Получение информации о пользователе", tags = ORGANIZER, responses = {
            @ApiResponse(responseCode = "200", description = "Получение данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getConfirmStatus(Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, organizerService.getConfirmStatus(user));
    }

    @GetMapping("/user-info-private")
    @Operation(summary = "Получение информации о пользователе", tags = ORGANIZER, responses = {
            @ApiResponse(responseCode = "200", description = "Получение данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = OrganizerDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Нет доступа", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    public ResponseEntity<?> getUserInfoWithPrivate(Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        return ResponseBuilder.build(OK, organizerService.getUserInfoWithPrivate(user));
    }

    @PutMapping("/profile/update/personal-information")
    @Operation(summary = "Обновление информации о пользователе", tags = ORGANIZER, responses = {
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
    public ResponseEntity<?> updateProfileInformation(@RequestBody @Valid UpdatePersonalInfoRequest request,
                                                      Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        organizerService.updateProfile(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/password")
    @Operation(summary = "Обновление информации о пароле", tags = ORGANIZER, responses = {
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
    public ResponseEntity<?> updateProfilePassword(@RequestBody @Valid UpdatePasswordRequest request,
                                                   Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        organizerService.updatePassword(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/email")
    @Operation(summary = "Обновление информации о email", tags = ORGANIZER, responses = {
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
    public ResponseEntity<?> updateProfileEmail(@RequestBody @Valid UpdateEmailRequest request,
                                                Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        authService.resendCode( request, user);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/passport")
    @Operation(summary = "Обновление информации о email", tags = ORGANIZER, responses = {
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
    public ResponseEntity<?> updateProfilePassport(@RequestBody @Valid UpdatePassportInfoRequest request,
                                                Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        organizerService.updatePassportInfo(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }

    @PutMapping("/profile/update/inn")
    @Operation(summary = "Обновление информации о email", tags = ORGANIZER, responses = {
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
    public ResponseEntity<?> updateProfileINN(@RequestBody @Valid UpdateINNRequest request,
                                                Authentication authentication) {
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        organizerService.updateINNInfo(user, request);
        return ResponseBuilder.buildWithoutBodyResponse(OK);
    }



    @DeleteMapping("/delete")
    @Operation(summary = "Удаление пользователя", tags = ORGANIZER, responses = {
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
        OrganizerDTO user = organizerService.getOrganizerByLogin((String) authentication.getPrincipal());
        organizerService.deleteOrganizer(user.getOrganizerId());
        return ResponseBuilder.buildWithoutBodyResponse(NO_CONTENT);
    }

}