package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "yearinmusic")
public class YearInMusic {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    private Integer year;

    @ManyToOne
    private Musician bestMaleSinger;

    @ManyToOne
    private Musician bestFemaleSinger;

    @ManyToOne
    private Musician bestGroup;

    @ManyToOne
    private Musician bestComposer;

    @Column(columnDefinition = "text")
    private String aotyListDescription;

    @Column(columnDefinition = "text")
    private String aotySpotifyLink;

    @Column(columnDefinition = "text")
    private String sotyListDescription;

    @Column(columnDefinition = "text")
    private String sotySpotifyLink;

}
