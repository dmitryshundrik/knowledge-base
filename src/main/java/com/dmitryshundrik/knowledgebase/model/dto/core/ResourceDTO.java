package com.dmitryshundrik.knowledgebase.model.dto.core;

import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {

    private String id;

    private String created;

    private String title;

    private String description;

    private String link;

    private ResourceType resourceType;
}
