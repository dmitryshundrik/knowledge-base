package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.dto.core.TimelineEventDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.TimelineEvent;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.enums.TimelineEventType;
import com.dmitryshundrik.knowledgebase.repository.core.TimelineEventRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TimelineEventService {

    private final TimelineEventRepository timelineEventRepository;

    public TimelineEvent getById(String id) {
        return timelineEventRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<TimelineEvent> getAllByType(TimelineEventType type) {
        return timelineEventRepository.findAllByTimelineEventTypeOrderByCreatedAsc(type);
    }

    public List<TimelineEvent> getAllMusicTimelineEventsBeforeCommon() {
        return timelineEventRepository
                .findAllByTimelineEventTypeAndEraTypeOrderByCreatedAsc(TimelineEventType.MUSIC, EraType.BEFORE_COMMON);
    }

    public List<TimelineEvent> getAllMusicTimelineEventsByCommonEra() {
        return timelineEventRepository
                .findAllByTimelineEventTypeAndEraTypeOrderByCreatedAsc(TimelineEventType.MUSIC, EraType.COMMON);
    }

    public TimelineEventDto createTimelineEvent(TimelineEventDto timelineEventDTO) {
        TimelineEvent timelineEvent = new TimelineEvent();
        setFieldsFromDto(timelineEvent, timelineEventDTO);
        return getTimelineEventDto(timelineEventRepository.save(timelineEvent));
    }

    public TimelineEventDto updateTimelineEvent(String eventId, TimelineEventDto timelineEventDTO) {
        TimelineEvent timelineEventById = getById(eventId);
        setFieldsFromDto(timelineEventById, timelineEventDTO);
        return getTimelineEventDto(timelineEventById);
    }

    public void deleteTimelineEvent(String eventId) {
        TimelineEvent timelineEventById = getById(eventId);
        timelineEventRepository.delete(timelineEventById);
    }

    public String createEventForTimelineOfMusic(TimelineEventDto timelineEventDTO) {
        TimelineEvent timelineEvent = getById(createTimelineEvent(timelineEventDTO).getId());
        timelineEvent.setTimelineEventType(TimelineEventType.MUSIC);
        return timelineEvent.getId().toString();
    }

    public TimelineEventDto getTimelineEventDto(TimelineEvent timelineEvent) {
        return TimelineEventDto.builder()
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

    public List<TimelineEventDto> getTimelineEventDtoList(List<TimelineEvent> timelineEventList) {
        return timelineEventList.stream().map(this::getTimelineEventDto).collect(Collectors.toList());
    }

    private void setFieldsFromDto(TimelineEvent timelineEvent, TimelineEventDto timelineEventDTO) {
        timelineEvent.setYear(timelineEventDTO.getYear());
        timelineEvent.setAnotherYear(timelineEventDTO.getAnotherYear());
        timelineEvent.setEraType(timelineEventDTO.getEraType());
        timelineEvent.setTimelineEventType(timelineEventDTO.getTimelineEventType());
        timelineEvent.setTitle(timelineEventDTO.getTitle());
        timelineEvent.setDescription(timelineEventDTO.getDescription());
    }
}
