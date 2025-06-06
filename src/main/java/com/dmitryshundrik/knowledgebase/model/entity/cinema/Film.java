package com.dmitryshundrik.knowledgebase.model.entity.cinema;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "film")
@Data
@EqualsAndHashCode(callSuper = true)
public class Film extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DIRECTOR")
    private String director;

    @Column(name = "STARRING")
    private String starring;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "YEAR_RANK")
    private Double yearRank;

    @Column(name = "ALL_TIME_RANK")
    private Double allTimeRank;

    @Column(name = "SYNOPSIS", columnDefinition = "text")
    private String synopsis;

    @Column(name = "IMAGE", columnDefinition = "text")
    private String image;
}
