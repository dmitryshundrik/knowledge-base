package com.dmitryshundrik.knowledgebase.model.literature;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "prose")
@Data
@EqualsAndHashCode(callSuper = true)
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

    @Column(name = "PLAY_CHARACTERS_SCHEMA", columnDefinition = "text")
    private String playCharactersSchema;

    @Column(name = "SYNOPSIS", columnDefinition = "text")
    private String synopsis;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prose")
    private List<Quote> quoteList;
}
