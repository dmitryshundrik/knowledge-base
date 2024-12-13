package com.dmitryshundrik.knowledgebase.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YearInMusicViewDTO {

    private String created;

    private String slug;

    private String title;

    private Integer year;

    private String bestMaleSinger;

    private String bestMaleSingerSlug;

    private String bestFemaleSinger;

    private String bestFemaleSingerSlug;

    private String bestGroup;

    private String bestGroupSlug;

    private String bestComposer;

    private String bestComposerSlug;

    private String aotyListDescription;

    private String aotySpotifyLink;

    private String sotyListDescription;

    private String sotySpotifyLink;
}
