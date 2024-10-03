package com.dmitryshundrik.knowledgebase.model;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CREATED")
    private Instant created;
}
