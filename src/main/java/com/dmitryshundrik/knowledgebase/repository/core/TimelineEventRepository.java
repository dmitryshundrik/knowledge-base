package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.TimelineEvent;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.enums.TimelineEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TimelineEventRepository extends JpaRepository<TimelineEvent, UUID> {

    List<TimelineEvent> findAllByTimelineEventTypeOrderByCreatedAsc(TimelineEventType type);

    List<TimelineEvent> findAllByTimelineEventTypeAndEraTypeOrderByCreatedAsc(TimelineEventType type, EraType eraType);
}
