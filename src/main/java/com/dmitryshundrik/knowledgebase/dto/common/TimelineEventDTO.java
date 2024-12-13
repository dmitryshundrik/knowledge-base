package com.dmitryshundrik.knowledgebase.dto.common;

import com.dmitryshundrik.knowledgebase.util.enums.EraType;
import com.dmitryshundrik.knowledgebase.util.enums.TimelineEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

import static com.dmitryshundrik.knowledgebase.util.Constants.DESCRIPTION_MUST_NOT_BE_BLANK;

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

    @NotBlank(message = DESCRIPTION_MUST_NOT_BE_BLANK)
    private String description;
}
