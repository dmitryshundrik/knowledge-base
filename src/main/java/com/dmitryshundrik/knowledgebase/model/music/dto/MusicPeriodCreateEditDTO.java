package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MusicPeriodCreateEditDTO {

    private String slug;

    private String title;

    private String titleEn;

    private Integer approximateStart;

    private Integer approximateEnd;

    private String description;

    public MusicPeriodCreateEditDTO() {

    }

}
