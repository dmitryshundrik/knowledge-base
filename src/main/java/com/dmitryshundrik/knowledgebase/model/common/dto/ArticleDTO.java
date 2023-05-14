package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class ArticleDTO {

    private String id;

    private Instant created;

    private String title;

    private String description;

    public ArticleDTO() {

    }

}
