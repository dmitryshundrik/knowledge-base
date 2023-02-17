package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;
import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    private String feature;

    private Integer year;

    @ManyToOne
    private Album album;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<AcademicGenre> academicGenres;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<ContemporaryGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    @Column(columnDefinition = "text")
    private String highlights;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text")
    private String lyrics;

    @Column(columnDefinition = "text")
    private String translation;

}
