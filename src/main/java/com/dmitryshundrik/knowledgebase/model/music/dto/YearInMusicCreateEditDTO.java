package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class YearInMusicCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private Integer year;

    private String bestMaleSingerId;

    private String bestFemaleSingerId;

    private String bestGroupId;

    private String bestComposerId;

    private String aotyListDescription;

    private String aotySpotifyLink;

    private String sotyListDescription;

    private String sotySpotifyLink;

    public YearInMusicCreateEditDTO() {

    }
}
