package com.dmitryshundrik.knowledgebase.model.dto.literature;

public record WriterArchiveListDto(
        String slug,
        String nickName,
        Integer born,
        Integer died,
        String birthplace,
        String occupation,
        Boolean dateNotification) {
}
