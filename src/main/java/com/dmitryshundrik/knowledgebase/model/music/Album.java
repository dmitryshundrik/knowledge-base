package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    private Musician musician;

    private String artwork;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    private String description;

    private Double rating;

    @OneToMany
    private List<Composition> compositions;

    @ElementCollection
    private List<String> highlights;
}
