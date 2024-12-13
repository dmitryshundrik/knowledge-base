package com.dmitryshundrik.knowledgebase.dto.art;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaintingStyleViewDto {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private String count;

    private String description;
}
