package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(unique = true, nullable = false)
    private String slug;

    private String firstName;

    private String lastName;

    @NotBlank
    private String nickName;

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

    private String spotifyLink;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    private List<Composition> compositions;

}
