package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProseSelectDTO {

    private String id;

    private String title;
}
