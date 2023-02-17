package com.dmitryshundrik.knowledgebase.model.timeline;

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

    @NotNull(message = "year may not be null")
    private Integer year;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank(message = "description may not be blank")
    private String description;

    public EventDTO () {

    }

}
