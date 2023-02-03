package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import com.dmitryshundrik.knowledgebase.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }

    public void updateEvent(EventDTO eventDTO, Long id) {
        Event eventById = getEventById(id);
        eventById.setYear(eventDTO.getYear());
        eventById.setDate(eventDTO.getDate());
        eventById.setDescription(eventDTO.getDescription());
    }

    public EventDTO eventToEventDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .year(event.getYear())
                .date(event.getDate())
                .description(event.getDescription())
                .build();
    }

    public List<EventDTO> eventListToEventDTOList(List<Event> eventList) {
        return eventList.stream().map(event -> eventToEventDTO(event)).collect(Collectors.toList());
    }
}
