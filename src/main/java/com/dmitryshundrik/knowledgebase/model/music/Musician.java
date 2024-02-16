package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
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

    @Column(name = "nick_name_en")
    private String nickNameEn;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(columnDefinition = "text")
    private String image;

    private Integer born;

    private Integer died;

    private Integer founded;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String birthplace;

    private String based;

    private String occupation;

    private String catalogTitle;

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
    private List<PersonEvent> events;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Album> albums;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    @JoinTable(name = "albums_collaborators", joinColumns = @JoinColumn(name = "collaborators_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private List<Album> collaborations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Composition> compositions;

}
