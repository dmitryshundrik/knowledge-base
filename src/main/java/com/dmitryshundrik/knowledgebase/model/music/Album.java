package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @NotBlank
    private String slug;

    @NotBlank
    private String title;

    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    @Column(columnDefinition = "text")
    private String artwork;

    private Integer year;

    @Enumerated(EnumType.STRING)
    private Period period;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<AcademicGenre> academicGenres;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<ContemporaryGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    private String highlights;

    private String description;

    @OneToMany
    private List<Composition> compositions;
}
