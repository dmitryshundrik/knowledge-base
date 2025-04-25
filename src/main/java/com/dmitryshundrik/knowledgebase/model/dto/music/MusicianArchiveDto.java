package com.dmitryshundrik.knowledgebase.model.dto.music;

public record MusicianArchiveDto(
        String slug,
        String nickName,
        Integer born,
        Integer died,
        Integer founded,
        String birthplace,
        String based,
        String occupation,
        Boolean dateNotification) {
}
