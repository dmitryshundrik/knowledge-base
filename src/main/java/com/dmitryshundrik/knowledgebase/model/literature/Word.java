package com.dmitryshundrik.knowledgebase.model.literature;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

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
