package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "composition")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Composition extends AbstractEntity {

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CATALOG_NUMBER")
    private Double catalogNumber;

    @ManyToOne
    private Musician musician;

    @Column(name = "FEATURE")
    private String feature;

    @Column(name = "YEAR")
    private Integer year;

    @ManyToOne
    private Album album;

    @ManyToMany
    private List<MusicGenre> musicGenres;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "YEAR_END_RANK")
    private Integer yearEndRank;

    @Column(name = "ESSENTIAL_COMPOSITIONS_RANK")
    private Integer essentialCompositionsRank;

    @Column(name = "HIGHLIGHTS", columnDefinition = "text")
    private String highlights;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "LYRICS", columnDefinition = "text")
    private String lyrics;

    @Column(name = "TRANSLATION", columnDefinition = "text")
    private String translation;
}
