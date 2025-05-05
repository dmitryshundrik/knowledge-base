package com.dmitryshundrik.knowledgebase.model.dto.music;

import java.time.Instant;

public record MusicianActivityDto(
        Instant created,
        String slug,
        String nickName) {

}
