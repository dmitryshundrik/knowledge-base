package com.dmitryshundrik.knowledgebase.model.literature;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "quotes")
public class Quote {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @ManyToOne
    private Writer writer;

    @ManyToOne
    private Prose prose;

    private String location;

    private Integer page;

    @Column(columnDefinition = "text")
    private String description;

    @Column(columnDefinition = "text", name = "description_html")
    private String descriptionHtml;

}
