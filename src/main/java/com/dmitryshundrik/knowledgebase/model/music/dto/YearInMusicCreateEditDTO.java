package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearInMusicCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    @NotNull(message = "year may not be null")
    private Integer year;

    private String bestMaleSingerId;

    private String bestFemaleSingerId;

    private String bestGroupId;

    private String bestComposerId;

    private String aotyListDescription;

    private String aotySpotifyLink;

    private String sotyListDescription;

    private String sotySpotifyLink;

}
