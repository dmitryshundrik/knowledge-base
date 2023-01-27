package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "timelines")
public class Timeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TimelineType type;

    private String title;

    @OneToMany
    private List<Event> events;
}
