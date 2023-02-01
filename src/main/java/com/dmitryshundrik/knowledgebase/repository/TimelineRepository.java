package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.timeline.Timeline;
import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    Timeline getTimelineByType(TimelineType type);
}
