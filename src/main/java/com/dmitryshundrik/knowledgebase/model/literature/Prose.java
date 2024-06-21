package com.dmitryshundrik.knowledgebase.model.literature;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prose")
@Data
public class Prose extends AbstractEntity {

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @ManyToOne
    private Writer writer;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prose")
    private List<Quote> quoteList;

}
