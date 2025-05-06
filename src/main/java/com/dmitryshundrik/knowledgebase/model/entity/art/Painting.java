package com.dmitryshundrik.knowledgebase.model.entity.art;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.entity.core.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
            name = "painting_painting_styles",
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
