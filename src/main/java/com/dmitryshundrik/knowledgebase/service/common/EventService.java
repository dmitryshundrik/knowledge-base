package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.enums.EventType;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.common.dto.EventDTO;
import com.dmitryshundrik.knowledgebase.repository.common.EventRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(UUID id) {
        return eventRepository.findById(id).orElse(null);
    }

    public List<Event> getAllEventsByType(EventType type) {
        return eventRepository.findAllByEventType(type);
    }

    public Event createEventByEventDTO(EventDTO eventDTO) {
        Event event = new Event();
        event.setCreated(Instant.now());
        setFieldsFromDTO(event, eventDTO);
        return eventRepository.save(event);
    }

    public UUID createEventForMusician(Musician musician, EventDTO eventDTO) {
        Event event = createEventByEventDTO(eventDTO);
        event.setEventType(EventType.ENTITY_TIMELINE);
        musician.getEvents().add(event);
        return event.getId();
    }

    public UUID createEventForTimelineOfMusic(EventDTO eventDTO) {
        Event event = createEventByEventDTO(eventDTO);
        event.setEventType(EventType.MUSIC_TIMELINE);
        return event.getId();
    }

    public void updateEvent(UUID eventId, EventDTO eventDTO) {
        Event eventById = getEventById(eventId);
        setFieldsFromDTO(eventById, eventDTO);
    }

    public void deleteMusicianEventById(Musician musician, UUID id) {
        musician.getEvents().removeIf(event -> event.getId().equals(id));
        eventRepository.deleteById(id);
    }

    public void deleteEventFromTimelineOfMusic(UUID eventId) {
        Event eventById = getEventById(eventId);
        eventRepository.delete(eventById);
    }

    public EventDTO getEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .created(Formatter.instantFormatterYMDHMS(event.getCreated()))
                .year(event.getYear())
                .anotherYear(event.getAnotherYear())
                .eraType(event.getEraType())
                .title(event.getTitle())
                .description(event.getDescription())
                .build();
    }

    public List<EventDTO> getEventDTOList(List<Event> eventList) {
        return eventList.stream().map(event -> getEventDTO(event)).collect(Collectors.toList());
    }

    private void setFieldsFromDTO(Event event, EventDTO eventDTO) {
        event.setYear(eventDTO.getYear());
        event.setAnotherYear(eventDTO.getAnotherYear());
        event.setEraType(eventDTO.getEraType());
        event.setTitle(eventDTO.getTitle());
        event.setDescription(eventDTO.getDescription());
    }
}
