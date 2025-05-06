package com.dmitryshundrik.knowledgebase.model.entity.literature;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
