package com.dmitryshundrik.knowledgebase.model.literature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class QuoteCreateEditDTO {

    private String id;

    private String writerNickname;

    private String writerSlug;

    private String proseId;

    private String location;

    private String description;

    public QuoteCreateEditDTO () {

    }

}
