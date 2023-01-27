package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.common.Timeline;
import com.dmitryshundrik.knowledgebase.model.common.TimelineType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    Timeline getTimelineByType(TimelineType type);
}
