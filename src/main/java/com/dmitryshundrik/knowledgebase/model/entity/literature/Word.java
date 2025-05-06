package com.dmitryshundrik.knowledgebase.model.entity.literature;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "word")
@Data
@EqualsAndHashCode(callSuper = true)
public class Word extends AbstractEntity {

    @ManyToOne
    private Writer writer;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
