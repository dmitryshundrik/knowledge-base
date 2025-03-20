package com.dmitryshundrik.knowledgebase.dto.music;

public record MusicianManagementResponseDto(
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
