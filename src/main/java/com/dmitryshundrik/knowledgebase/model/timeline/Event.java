package com.dmitryshundrik.knowledgebase.model.timeline;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private Integer year;

    private LocalDate date;

    @Column(columnDefinition = "text")
    private String description;

}
