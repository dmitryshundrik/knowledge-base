package com.dmitryshundrik.knowledgebase.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoundationDto {

    private String id;

    private String created;

    private String title;

    private String description;

    private String link;
}
