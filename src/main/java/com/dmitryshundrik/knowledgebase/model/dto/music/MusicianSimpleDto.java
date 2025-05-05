package com.dmitryshundrik.knowledgebase.model.dto.music;

public record MusicianSimpleDto(
        String slug,
        String nickName,
        Integer born,
        Integer founded
) {}
