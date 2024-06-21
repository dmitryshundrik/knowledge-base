package com.dmitryshundrik.knowledgebase.model.common.dto;

import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.TimelineEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimelineEventDTO {

    private String id;

    private String created;

    private EraType eraType;

    private TimelineEventType timelineEventType;

    private Integer year;

    private Integer anotherYear;

    private String title;

    @NotBlank(message = "description may not be blank")
    private String description;

}
