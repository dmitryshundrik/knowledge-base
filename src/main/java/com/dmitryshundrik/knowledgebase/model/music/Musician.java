package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "musicians")
public class Musician {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(unique = true)
    private String slug;

    private String nickName;

    private String firstName;

    private String lastName;

    @Column(columnDefinition = "text")
    private String image;

    private Integer born;

    private Integer died;

    private LocalDate birthDate;

    private LocalDate deathDate;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<AcademicGenre> academicGenres;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<ContemporaryGenre> contemporaryGenres;

    @Column(columnDefinition = "text")
    private String spotifyLink;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("year")
    private List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("year")
    private List<Album> albums;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("year")
    private List<Composition> compositions;


}
