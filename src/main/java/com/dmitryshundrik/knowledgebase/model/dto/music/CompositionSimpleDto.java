package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import java.time.Instant;

public record CompositionSimpleDto(
        String created,
        String title,
        String musicianNickname,
        String musicianSlug,
        Integer year,
        Double rating) {

    public CompositionSimpleDto(Instant created, String title, String musicianNickname, String musicianSlug, Integer year,
                                Double rating) {
        this(created != null ? InstantFormatter.instantFormatterDMY(created) : null, title, musicianNickname,
                musicianSlug, year, rating);
    }
}
