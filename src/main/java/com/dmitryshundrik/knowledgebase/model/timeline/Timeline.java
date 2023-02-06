package com.dmitryshundrik.knowledgebase.model.timeline;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "timelines")
public class Timeline {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TimelineType type;

    private String title;

    @OneToMany
    private List<Event> events;
}
