package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String title;

    private String slug;

    @Column(columnDefinition = "text")
    private String description;

    private String data;

}
