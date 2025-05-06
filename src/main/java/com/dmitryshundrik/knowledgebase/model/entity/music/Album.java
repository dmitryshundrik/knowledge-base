package com.dmitryshundrik.knowledgebase.model.entity.music;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "album")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album extends AbstractEntity {

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CATALOG_NUMBER")
    private String catalogNumber;

    @ManyToOne
    private Musician musician;

    @ManyToMany
    private List<Musician> collaborators;

    @Column(name = "FEATURE")
    private String feature;

    @Column(name = "ARTWORK", columnDefinition = "text")
    private String artwork;

    @Column(name = "YEAR")
    private Integer year;

    @ManyToMany
    private List<MusicGenre> musicGenres;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "YEAR_END_RANK")
    private Integer yearEndRank;

    @Column(name = "ESSENTIAL_ALBUMS_RANK")
    private Integer essentialAlbumsRank;

    @Column(name = "HIGHLIGHTS", columnDefinition = "text")
    private String highlights;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "album")
    private List<Composition> compositions;
}
