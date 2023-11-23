package com.dmitryshundrik.knowledgebase.model.literature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WordDTO {

    private String id;

    private String created;

    private String writerNickname;

    private String writerSlug;

    private String title;

    private String description;

    public WordDTO() {

    }
}
