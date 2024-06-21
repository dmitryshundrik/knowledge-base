package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "painting_styles")
@Data
@EqualsAndHashCode(callSuper = true)
public class PaintingStyle extends AbstractEntity {

    @Column(unique = true)
    private String slug;

    private String title;

    private String titleEn;

    private Integer count;

    @Column(columnDefinition = "text")
    private String description;

}
