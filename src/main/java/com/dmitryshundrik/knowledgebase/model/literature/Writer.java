package com.dmitryshundrik.knowledgebase.model.literature;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

@Data
@Entity
@Table(name = "writers")
public class Writer {

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

    private String birthplace;

    private String occupation;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<PersonEvent> events = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Prose> proseList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Quote> quoteList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Word> wordList = new ArrayList<>();

}
