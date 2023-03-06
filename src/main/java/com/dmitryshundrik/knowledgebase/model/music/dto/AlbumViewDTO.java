package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlbumViewDTO {

    private String created;

    private String slug;

    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String feature;

    private String artwork;

    private Integer year;

    private List<MusicPeriod> musicPeriods;

    private List<MusicGenre> musicGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialAlbumsRank;

    private String highlights;

    private String description;

    private List<CompositionViewDTO> compositions;
}
