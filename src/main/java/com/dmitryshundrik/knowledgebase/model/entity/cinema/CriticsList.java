package com.dmitryshundrik.knowledgebase.model.entity.cinema;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "critics_list")
@Data
@EqualsAndHashCode(callSuper = true)
public class CriticsList extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "SYNOPSIS", columnDefinition = "text")
    private String synopsis;
}
