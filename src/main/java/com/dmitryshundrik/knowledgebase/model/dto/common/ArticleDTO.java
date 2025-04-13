package com.dmitryshundrik.knowledgebase.model.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private String id;

    private Instant created;

    private String title;

    private String description;
}
