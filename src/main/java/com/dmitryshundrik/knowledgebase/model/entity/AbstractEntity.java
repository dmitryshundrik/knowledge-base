package com.dmitryshundrik.knowledgebase.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "CREATED", updatable = false)
    @CreationTimestamp
    private Instant created;
}
