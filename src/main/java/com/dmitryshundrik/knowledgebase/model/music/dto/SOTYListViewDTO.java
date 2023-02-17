package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class SOTYListViewDTO {

    private Instant created;

    private String slug;

    private String title;

    private Integer year;

    private String description;

    private String spotifyLink;

}
