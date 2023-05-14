package com.dmitryshundrik.knowledgebase.model.common;

import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.TimelineEventType;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "timelineevents")
public class TimelineEvent {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Enumerated(EnumType.STRING)
    private EraType eraType;

    @Enumerated(EnumType.STRING)
    private TimelineEventType timelineEventType;

    private Integer year;

    private Integer anotherYear;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

}
