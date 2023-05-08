package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResourceDTO {

    private String id;

    private String created;

    private String title;

    private String description;

    private String link;

    public ResourceDTO() {

    }

}
