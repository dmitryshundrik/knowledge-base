package com.dmitryshundrik.knowledgebase.model.art.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaintingStyleViewDTO {

    private String created;

    private String slug;

    private String title;

    private String titleEn;

    private String count;

    private String description;

}
