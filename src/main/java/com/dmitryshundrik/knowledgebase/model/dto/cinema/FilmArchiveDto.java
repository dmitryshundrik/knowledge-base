package com.dmitryshundrik.knowledgebase.model.dto.cinema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmArchiveDto {

    private Instant created;

    private String slug;

    private String title;

    private String director;

    private String starring;

    private Integer year;

    private Double rating;

    private Double yearRank;

    private Double allTimeRank;
}
