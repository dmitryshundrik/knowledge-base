package com.dmitryshundrik.knowledgebase.model.literature;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "prose")
public class Prose {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    @ManyToOne
    private Writer writer;

    private Integer year;

    private Double rating;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prose")
    private List<Quote> quoteList;

}
