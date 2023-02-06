package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue
    private UUID id;

    private String slug;

    private String title;

    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    private String feature;

    private Integer year;

    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Genre> genres;

    private String lyric;

    private String translation;

    private String description;

    private Double rating;

    private Integer yearEndRank;

    @ElementCollection
    private List<String> highlights;

}
