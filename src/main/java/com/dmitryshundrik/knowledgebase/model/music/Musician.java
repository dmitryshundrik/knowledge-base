package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "musicians")
public class Musician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String firstName;

    private String lastName;

    private String nickName;

    private String image;

    private LocalDate born;

    private LocalDate died;

    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Style> styles;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    @OneToMany
    private List<Event> events;

    @OneToMany
    private List<Composition> compositions;

}
