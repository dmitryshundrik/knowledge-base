package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    @ManyToOne
    private Painter painter;

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

    private Integer painterTopRank;

    private Integer allTimeTopRank;

    @Column(columnDefinition = "text")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

}
