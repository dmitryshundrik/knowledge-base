package com.dmitryshundrik.knowledgebase.model.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicGenreViewDto {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private String musicGenreType;

    private Integer count;

    private String description;
}
