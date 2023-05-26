package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.dto.TimelineEventDTO;
import com.dmitryshundrik.knowledgebase.model.common.TimelineEvent;
import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.TimelineEventType;
import com.dmitryshundrik.knowledgebase.repository.common.TimelineEventRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class TimelineEventService {

    @Autowired
    private TimelineEventRepository timelineEventRepository;

    public TimelineEvent getTimelineEventById(String id) {
        return timelineEventRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<TimelineEvent> getAllEventsByType(TimelineEventType type) {
        return timelineEventRepository.findAllByTimelineEventType(type).stream()
                .sorted(Comparator.comparing(TimelineEvent::getCreated))
                .collect(Collectors.toList());
    }

    public List<TimelineEvent> getAllMusicTimelineEventsBeforeCommon() {
        return getAllEventsByType(TimelineEventType.MUSIC).stream()
                .filter(timelineEvent -> timelineEvent.getEraType().equals(EraType.BEFORE_COMMON))
                .sorted((o1, o2) -> o2.getYear().compareTo(o1.getYear()))
                .collect(Collectors.toList());
    }

    public List<TimelineEvent> getAllMusicTimelineEventsByCommonEra() {
        return getAllEventsByType(TimelineEventType.MUSIC).stream()
                .filter(timelineEvent -> timelineEvent.getEraType().equals(EraType.COMMON))
                .sorted(Comparator.comparing(TimelineEvent::getYear))
                .collect(Collectors.toList());
    }

    public TimelineEventDTO createTimelineEvent(TimelineEventDTO timelineEventDTO) {
        TimelineEvent timelineEvent = new TimelineEvent();
        timelineEvent.setCreated(Instant.now());
        setFieldsFromDTO(timelineEvent, timelineEventDTO);
        return getTimelineEventDTO(timelineEventRepository.save(timelineEvent));
    }

    public TimelineEventDTO updateTimelineEvent(String eventId, TimelineEventDTO timelineEventDTO) {
        TimelineEvent timelineEventById = getTimelineEventById(eventId);
        setFieldsFromDTO(timelineEventById, timelineEventDTO);
        return getTimelineEventDTO(timelineEventById);
    }

    public void deleteTimelineEvent(String eventId) {
        TimelineEvent timelineEventById = getTimelineEventById(eventId);
        timelineEventRepository.delete(timelineEventById);
    }

    public String createEventForTimelineOfMusic(TimelineEventDTO timelineEventDTO) {
        TimelineEvent timelineEvent = getTimelineEventById(createTimelineEvent(timelineEventDTO).getId());
        timelineEvent.setTimelineEventType(TimelineEventType.MUSIC);
        return timelineEvent.getId().toString();
    }

    public TimelineEventDTO getTimelineEventDTO(TimelineEvent timelineEvent) {
        return TimelineEventDTO.builder()
                .id(timelineEvent.getId().toString())
                .created(InstantFormatter.instantFormatterYMDHMS(timelineEvent.getCreated()))
                .year(timelineEvent.getYear())
                .anotherYear(timelineEvent.getAnotherYear())
                .eraType(timelineEvent.getEraType())
                .timelineEventType(timelineEvent.getTimelineEventType())
                .title(timelineEvent.getTitle())
                .description(timelineEvent.getDescription())
                .build();
    }

    public List<TimelineEventDTO> getTimelineEventDTOList(List<TimelineEvent> timelineEventList) {
        return timelineEventList.stream().map(this::getTimelineEventDTO).collect(Collectors.toList());
    }

    private void setFieldsFromDTO(TimelineEvent timelineEvent, TimelineEventDTO timelineEventDTO) {
        timelineEvent.setYear(timelineEventDTO.getYear());
        timelineEvent.setAnotherYear(timelineEventDTO.getAnotherYear());
        timelineEvent.setEraType(timelineEventDTO.getEraType());
        timelineEvent.setTimelineEventType(timelineEventDTO.getTimelineEventType());
        timelineEvent.setTitle(timelineEventDTO.getTitle());
        timelineEvent.setDescription(timelineEventDTO.getDescription());
    }
}
