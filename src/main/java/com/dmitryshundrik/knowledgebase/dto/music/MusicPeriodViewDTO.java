package com.dmitryshundrik.knowledgebase.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicPeriodViewDTO {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private Integer approximateStart;

    private Integer approximateEnd;

    private String description;
}
