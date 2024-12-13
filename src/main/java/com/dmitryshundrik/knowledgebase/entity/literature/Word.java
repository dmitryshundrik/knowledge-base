package com.dmitryshundrik.knowledgebase.entity.literature;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
