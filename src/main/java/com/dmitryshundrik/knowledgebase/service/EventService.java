package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import com.dmitryshundrik.knowledgebase.repository.EventRepository;
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

    public Event createEventByEventDTO(EventDTO eventDTO) {
        Event event = new Event();
        event.setCreated(Instant.now());
        setFieldsFromDTO(event, eventDTO);
        return eventRepository.save(event);
    }

    public UUID createMusicianEventByEventDTO(EventDTO eventDTO, Musician musician) {
        Event eventByEventDTO = createEventByEventDTO(eventDTO);
        musician.getEvents().add(eventByEventDTO);
        return eventByEventDTO.getId();
    }

    public void updateEvent(EventDTO eventDTO, UUID id) {
        Event eventById = getEventById(id);
        eventById.setYear(eventDTO.getYear());
        eventById.setDate(eventDTO.getDate());
        eventById.setDescription(eventDTO.getDescription());
    }

    public void deleteMusicianEventById(Musician musician, UUID id) {
        eventRepository.deleteById(id);
        musician.getEvents().removeIf(event -> event.getId().equals(id));
    }

    public EventDTO eventToEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .created(Formatter.instantFormatter(event.getCreated()))
                .year(event.getYear())
                .date(event.getDate())
                .description(event.getDescription())
                .build();
    }

    public List<EventDTO> eventListToEventDTOList(List<Event> eventList) {
        return eventList.stream().map(event -> eventToEventDTO(event)).collect(Collectors.toList());
    }

    private void setFieldsFromDTO(Event event, EventDTO eventDTO) {
        event.setYear(eventDTO.getYear());
        event.setDate(eventDTO.getDate());
        event.setDescription(eventDTO.getDescription());
    }
}
