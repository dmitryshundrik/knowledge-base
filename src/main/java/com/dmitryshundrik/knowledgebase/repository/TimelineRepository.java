package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.timeline.Timeline;
import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimelineRepository extends JpaRepository<Timeline, UUID> {

    Timeline getTimelineByType(TimelineType type);
}
