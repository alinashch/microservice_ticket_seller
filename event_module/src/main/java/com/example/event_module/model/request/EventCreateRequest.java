package com.example.event_module.model.request;

import com.example.event_module.model.entity.PlaceTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventCreateRequest {

    @NotBlank(message = "ID организатора  не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long organizerId;

    @NotBlank(message = "Имя не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 5, max = 45, message = "Имя не может быть меньше 5 и больше 45 символов")
    private String name;

    @NotBlank(message = "Имя группы не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(min = 5, max = 100, message = "Имя группы не может быть меньше 5 и больше 100 символов")
    private String nameGroup;

    @NotBlank(message = "Описание не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    @Size(max = 400, message = "Описание не может быть  больше 400 символов")
    private String description;

    @NotBlank(message = "ID места и времени  не может быть пустым")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long placeTimeId;

}
