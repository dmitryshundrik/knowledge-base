package com.dmitryshundrik.knowledgebase.model.dto.literature;

public record WriterSimpleDto(
        String slug,
        String nickName,
        Integer born,
        Integer died
) {
}
