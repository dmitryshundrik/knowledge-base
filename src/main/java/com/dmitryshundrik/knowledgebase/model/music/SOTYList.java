package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "sotylists")
public class SOTYList {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    private Integer year;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String spotifyLink;

}
