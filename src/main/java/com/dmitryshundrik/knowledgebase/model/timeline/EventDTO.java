package com.dmitryshundrik.knowledgebase.model.timeline;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class EventDTO {

    private Long id;

    private Integer year;

    private LocalDate date;

    private String description;

}
