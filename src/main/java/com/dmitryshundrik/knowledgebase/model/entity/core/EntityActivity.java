package com.dmitryshundrik.knowledgebase.model.entity.core;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class EntityActivity {

    private Instant created;

    private String timeStamp;

    private String archiveSection;

    private String description;

    private boolean isNew;

    private String link;
}
