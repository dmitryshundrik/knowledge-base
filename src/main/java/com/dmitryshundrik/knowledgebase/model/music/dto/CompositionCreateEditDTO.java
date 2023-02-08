package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CompositionCreateEditDTO {

    private String slug;

    private String title;

    private String catalogNumber;

    private Musician musician;

    private String feature;

    private Integer year;

    private Period period;

    private List<AcademicGenre> academicGenres;

    private List<ContemporaryGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    private String highlights;

    private String description;

    private String lyrics;

    private String translation;

    public CompositionCreateEditDTO () {

    }
}
