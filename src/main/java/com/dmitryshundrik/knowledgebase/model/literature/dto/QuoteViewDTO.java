package com.dmitryshundrik.knowledgebase.model.literature.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteViewDTO {

    private String id;

    private String created;

    private String writerNickname;

    private String writerSlug;

    private String proseTitle;

    private String proseSlug;

    private String location;

    private String description;

}