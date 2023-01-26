package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "sotylists")
public class SOTYList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String slug;

    private String title;

    private LocalDate date;

    private String description;

    private String spotifyLink;

    @OneToMany(cascade = CascadeType.ALL)
    private List<SOTYListComposition> sotyListCompositions;
}
