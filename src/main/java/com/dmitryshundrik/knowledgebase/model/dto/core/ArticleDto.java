package com.dmitryshundrik.knowledgebase.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private String id;

    private Instant created;

    private String title;

    private String description;
}
