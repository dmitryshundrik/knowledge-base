package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.entity.common.TimelineEvent;
import com.dmitryshundrik.knowledgebase.util.enums.EraType;
import com.dmitryshundrik.knowledgebase.util.enums.TimelineEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TimelineEventRepository extends JpaRepository<TimelineEvent, UUID> {

    List<TimelineEvent> findAllByTimelineEventTypeOrderByCreatedAsc(TimelineEventType type);

    List<TimelineEvent> findAllByTimelineEventTypeAndEraTypeOrderByCreatedAsc(TimelineEventType type, EraType eraType);
}
