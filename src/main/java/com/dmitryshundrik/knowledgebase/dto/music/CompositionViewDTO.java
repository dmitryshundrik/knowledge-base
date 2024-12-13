package com.dmitryshundrik.knowledgebase.dto.music;

import com.dmitryshundrik.knowledgebase.entity.music.MusicGenre;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CompositionViewDTO {

    private String created;

    private String slug;

    private String title;

    private String catalogTitle;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String albumId;

    private String albumTitle;

    private String feature;

    private Integer year;

    private List<MusicGenre> musicGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialCompositionsRank;

    private String highlights;

    private String description;

    private String lyrics;

    private String translation;
}
