package com.dmitryshundrik.knowledgebase.model.dto.music;

import java.util.UUID;

public record MusicianSelectDto(
        String id,
        String slug,
        String nickName
) {
    public MusicianSelectDto(UUID id, String slug, String nickName) {
        this(id.toString(), slug, nickName);
    }
}
