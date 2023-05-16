package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "musicperiods")
public class MusicPeriod {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(unique = true)
    private String slug;

    private String title;

    private String titleEn;

    private Integer approximateStart;

    private Integer approximateEnd;

    @Column(columnDefinition = "text")
    private String description;

}
