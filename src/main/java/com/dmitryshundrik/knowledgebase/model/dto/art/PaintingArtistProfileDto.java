package com.dmitryshundrik.knowledgebase.model.dto.art;

public record PaintingArtistProfileDto(
        String created,
        String slug,
        String title,
        Integer year1,
        Integer year2,
        String approximateYears,
        String based,
        Integer artistTopRank,
        Integer allTimeTopRank
) {
}
