package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "personevents")
public class PersonEvent {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private Integer year;

    private Integer anotherYear;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

}
