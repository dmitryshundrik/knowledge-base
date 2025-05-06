package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "image")
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "DATA")
    private String data;
}
