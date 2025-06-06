package com.dmitryshundrik.knowledgebase.model.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.TITLE_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmCreateEditDto {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    private String title;

    private String director;

    private String starring;

    private Integer year;

    private Double rating;

    private Double yearRank;

    private Double allTimeRank;

    private String synopsis;

    private String image;
}
