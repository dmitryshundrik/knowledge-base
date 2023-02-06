package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "sotylists")
public class SOTYList {

    @Id
    @GeneratedValue
    private UUID id;

    private String slug;

    private String title;

    private Integer year;

    private String description;

    private String spotifyLink;

}
