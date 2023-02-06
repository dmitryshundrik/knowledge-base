package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
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

    private String slug;

    private String firstName;

    private String lastName;

    private String nickName;

    private String image;

    private Integer born;

    private Integer died;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Style> styles;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    private List<Composition> compositions;

}
