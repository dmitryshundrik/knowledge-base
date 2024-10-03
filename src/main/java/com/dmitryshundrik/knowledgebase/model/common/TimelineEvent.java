package com.dmitryshundrik.knowledgebase.model.common;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.TimelineEventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Entity
@Table(name = "timeline_event")
@Data
@EqualsAndHashCode(callSuper = true)
public class TimelineEvent extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "ERA_TYPE")
    private EraType eraType;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIMELINE_EVENT_TYPE")
    private TimelineEventType timelineEventType;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "ANOTHER_YEAR")
    private Integer anotherYear;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
