package com.dmitryshundrik.knowledgebase.model.art;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "painting_styles")
public class PaintingStyle {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(unique = true)
    private String slug;

    private String title;

    private String titleEn;

    private Integer count;

    @Column(columnDefinition = "text")
    private String description;

}
