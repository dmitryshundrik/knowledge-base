package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;
import javax.persistence.*;
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

    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    private String artwork;

    private Integer year;

    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    private String description;

    private Double rating;

    private Integer yearEndRank;

    @OneToMany
    private List<Composition> compositions;
}
