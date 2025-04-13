package com.dmitryshundrik.knowledgebase.model.dto.cinema;

import java.time.Instant;

public record CriticsListResponseDto(
        Instant created,
        String slug,
        String title,
        Integer year,
        String synopsis) {
}
