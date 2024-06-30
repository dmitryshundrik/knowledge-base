package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "painting")
@Data
@EqualsAndHashCode(callSuper = true)
public class Painting extends AbstractEntity {

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    private Artist artist;

    @Column(name = "YEAR1")
    private Integer year1;

    @Column(name = "YEAR2")
    private Integer year2;

    @Column(name = "APPROXIMATE_YEARS")
    private String approximateYears;

    @ManyToMany
    @JoinTable(
            name = "paintings_painting_styles",
            joinColumns = {@JoinColumn(name = "paintings_id")},
            inverseJoinColumns = {@JoinColumn(name = "painting_styles_id")}
    )
    private List<PaintingStyle> paintingStyles;

    @Column(name = "BASED")
    private String based;

    @Column(name = "ARTIST_TOP_RANK")
    private Integer artistTopRank;

    @Column(name = "ALL_TIME_TOP_RANK")
    private Integer allTimeTopRank;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
