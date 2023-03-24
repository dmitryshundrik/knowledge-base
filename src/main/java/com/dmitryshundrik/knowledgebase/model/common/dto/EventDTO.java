package com.dmitryshundrik.knowledgebase.model.common.dto;

import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class EventDTO {

    private UUID id;

    private String created;

    private EventType eventType;

    private EraType eraType;

    private Integer year;

    private Integer anotherYear;

    private String title;

    @NotBlank(message = "description may not be blank")
    private String description;

    public EventDTO () {

    }

}
