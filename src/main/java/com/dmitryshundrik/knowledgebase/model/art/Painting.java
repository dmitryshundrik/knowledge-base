package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "paintings")
@Data
@EqualsAndHashCode(callSuper = true)
public class Painting extends AbstractEntity {

    private String slug;

    private String title;

    @ManyToOne
    private Artist artist;

    @Column(name = "year1")
    private Integer year1;

    @Column(name = "year2")
    private Integer year2;

    private String approximateYears;

    @ManyToMany
    @JoinTable(
            name = "paintings_painting_styles",
            joinColumns = {@JoinColumn(name = "paintings_id")},
            inverseJoinColumns = {@JoinColumn(name = "painting_styles_id")}
    )
    private List<PaintingStyle> paintingStyles;

    private String based;

    private Integer artistTopRank;

    private Integer allTimeTopRank;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
