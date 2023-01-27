package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.common.Timeline;
import com.dmitryshundrik.knowledgebase.model.common.TimelineType;
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
