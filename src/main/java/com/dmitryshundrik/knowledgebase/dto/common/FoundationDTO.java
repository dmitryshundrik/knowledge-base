package com.dmitryshundrik.knowledgebase.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoundationDTO {

    private String id;

    private String created;

    private String title;

    private String description;

    private String link;
}
