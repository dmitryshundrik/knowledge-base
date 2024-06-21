package com.dmitryshundrik.knowledgebase.model.literature;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "words")
@Data
public class Word extends AbstractEntity {

    @ManyToOne
    private Writer writer;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

}
