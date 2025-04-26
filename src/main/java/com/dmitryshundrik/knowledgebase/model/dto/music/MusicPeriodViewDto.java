package com.dmitryshundrik.knowledgebase.model.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicPeriodViewDto {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private Integer approximateStart;

    private Integer approximateEnd;

    private String description;
}
