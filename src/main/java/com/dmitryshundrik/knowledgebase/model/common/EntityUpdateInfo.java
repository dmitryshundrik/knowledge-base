package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class EntityUpdateInfo {

    private Instant created;

    private String archiveSection;

    private String description;

    private String link;

}
