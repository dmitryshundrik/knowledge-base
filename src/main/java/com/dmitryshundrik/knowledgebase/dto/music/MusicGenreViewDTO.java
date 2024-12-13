package com.dmitryshundrik.knowledgebase.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicGenreViewDTO {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private String musicGenreType;

    private Integer count;

    private String description;
}
