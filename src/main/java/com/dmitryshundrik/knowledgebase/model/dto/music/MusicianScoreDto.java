package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;

public record MusicianScoreDto(
        Musician musician,
        double score) {
}