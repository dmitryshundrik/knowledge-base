package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MusicGenreCreateEditDTO {

    private String slug;

    private String title;

    private String titleEn;

    private MusicGenreType musicGenreType;

    private Integer count;

    private String description;

    public MusicGenreCreateEditDTO() {

    }
}
