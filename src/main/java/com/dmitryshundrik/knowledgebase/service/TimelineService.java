package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.Timeline;
import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import com.dmitryshundrik.knowledgebase.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;

    public Timeline getTimeline(TimelineType type) {
        Timeline timeline = timelineRepository.getTimelineByType(type);
        if (timeline == null) {
            return new Timeline();
        }
        return timeline;
    }

}
