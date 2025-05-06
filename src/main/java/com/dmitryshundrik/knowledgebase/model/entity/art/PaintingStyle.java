package com.dmitryshundrik.knowledgebase.model.entity.art;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "painting_style")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaintingStyle extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TITLE_EN")
    private String titleEn;

    @Column(name = "COUNT")
    private Integer count;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
