package com.example.event_module.controller;


import com.example.event_module.model.dto.EventDTO;
import com.example.event_module.model.dto.ExceptionDTO;
import com.example.event_module.model.request.EventCreateRequest;
import com.example.event_module.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.event_module.model.constant.Tag.EVENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping("/event")
public class EventController {

    private final EventService eventService;

    @PostMapping("/create-event")
    @Operation(summary = "Регистрация нового пользователя", tags = EVENT, responses = {
            @ApiResponse(responseCode = "201", description = "Мероприятие создано", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = EventDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Невалидные входные данные", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDTO.class))
            })
    })
    @SecurityRequirements
    public ResponseEntity<?> signUpNewUserUsingForm(@RequestBody @Valid EventCreateRequest eventCreateRequest) {
        return ResponseBuilder.build(OK, eventService.createEvent(eventCreateRequest));
    }

}
