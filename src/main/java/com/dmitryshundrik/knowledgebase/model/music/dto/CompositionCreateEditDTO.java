package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CompositionCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String albumId;

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

    private String lyrics;

    private String translation;

    public CompositionCreateEditDTO () {

    }
}
