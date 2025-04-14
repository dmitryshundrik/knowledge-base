package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.enums.TimelineEventType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

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
