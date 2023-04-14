package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.model.common.Event;
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
//@ToString(exclude = {"albums", "compositions"})
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

    private Integer founded;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String birthplace;

    private String occupation;

    @ManyToMany
    private List<MusicPeriod> musicPeriods;

    @Enumerated(EnumType.STRING)
    private SortType albumsSortType;

    @Enumerated(EnumType.STRING)
    private SortType compositionsSortType;

    @Column(columnDefinition = "text")
    private String spotifyLink;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<Event> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Album> albums;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Composition> compositions;

}
