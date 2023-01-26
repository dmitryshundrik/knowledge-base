package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String title;

    private String catalogNumber;

    @OneToOne(cascade = CascadeType.ALL)
    private Musician musician;

    private String feature;

    private LocalDate date;

    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Lyric> lyrics;

    private String description;

    private Double rating;

    @ElementCollection
    private List<String> highlights;

}
