package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "musician")
@Data
@EqualsAndHashCode(callSuper = true)
public class Musician extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "NICK_NAME_EN")
    private String nickNameEn;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender;

    @Column(name = "IMAGE", columnDefinition = "text")
    private String image;

    @Column(name = "BORN")
    private Integer born;

    @Column(name = "DIED")
    private Integer died;

    @Column(name = "FOUNDED")
    private Integer founded;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "DEATH_DATE")
    private LocalDate deathDate;

    @Column(name = "BIRTHPLACE")
    private String birthplace;

    @Column(name = "BASED")
    private String based;

    @Column(name = "OCCUPATION")
    private String occupation;

    @Column(name = "CATALOG_TITLE")
    private String catalogTitle;

    @ManyToMany
    private List<MusicPeriod> musicPeriods;

    @Enumerated(EnumType.STRING)
    @Column(name = "ALBUMS_SORT_TYPE")
    private SortType albumsSortType;

    @Enumerated(EnumType.STRING)
    @Column(name = "COMPOSITIONS_SORT_TYPE")
    private SortType compositionsSortType;

    @Column(name = "SPOTIFY_LINK", columnDefinition = "text")
    private String spotifyLink;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<PersonEvent> events;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Album> albums;

    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    @JoinTable(name = "album_collaborators", joinColumns = @JoinColumn(name = "collaborators_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private List<Album> collaborations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musician")
    @OrderBy("created")
    private List<Composition> compositions;

}
