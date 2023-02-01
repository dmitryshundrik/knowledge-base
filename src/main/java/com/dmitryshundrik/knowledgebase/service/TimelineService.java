package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.timeline.Timeline;
import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import com.dmitryshundrik.knowledgebase.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;

    public Timeline getTimeline(TimelineType type) {
        return timelineRepository.getTimelineByType(type);
    }
}
