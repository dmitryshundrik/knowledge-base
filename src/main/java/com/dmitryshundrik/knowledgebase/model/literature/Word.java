package com.dmitryshundrik.knowledgebase.model.literature;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @ManyToOne
    private Writer writer;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

}
