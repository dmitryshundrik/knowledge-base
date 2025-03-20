package com.dmitryshundrik.knowledgebase.dto.cinema;

public record FilmResponseDto(
        String slug,
        String title,
        String director,
        String starring,
        Integer year,
        Double rating,
        Double yearRank,
        Double allTimeRank,
        String synopsis,
        String image) {
}
