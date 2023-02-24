package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompositionViewDTO {

    private String created;

    private String slug;

    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String albumId;

    private String albumTitle;

    private String feature;

    private Integer year;

    private Period period;

    private List<AcademicGenre> academicGenres;

    private List<ContemporaryGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialCompositionsRank;

    private String highlights;

    private String description;

}
