package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    @ManyToMany
    private List<Musician> collaborators;

    private String feature;

    @Column(columnDefinition = "text")
    private String artwork;

    private Integer year;

    @ManyToMany
    private List<MusicGenre> musicGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialAlbumsRank;

    @Column(columnDefinition = "text")
    private String highlights;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "album")
    private List<Composition> compositions;
}
