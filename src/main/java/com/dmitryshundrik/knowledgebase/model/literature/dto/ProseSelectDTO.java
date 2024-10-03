package com.dmitryshundrik.knowledgebase.model.literature.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProseSelectDTO {

    private String id;

    private String title;
}
