package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

}
