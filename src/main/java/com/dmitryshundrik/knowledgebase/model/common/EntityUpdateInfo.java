package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EntityUpdateInfo {

    private String created;

    private String archiveSection;

    private String description;

    private String link;

}
