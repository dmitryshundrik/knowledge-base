package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.common.dto.EventDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.EventType;
import com.dmitryshundrik.knowledgebase.service.common.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class TimelineOfMusicManagementController {

    @Autowired
    private EventService eventService;

    @GetMapping("/management/timeline-of-music/event/all")
    public String getAllEventsForTimelineOfMusic(Model model) {
        model.addAttribute("events", eventService
                .getEventDTOList(eventService.getAllEventsByType(EventType.MUSIC_TIMELINE)));
        return "management/music/timeline-of-music-all";
    }

    @GetMapping("/management/timeline-of-music/event/create")
    public String getCreateEventForTimelineOfMusic(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("eraTypes", EraType.values());
        return "management/music/timeline-of-music-create";
    }

    @PostMapping("/management/timeline-of-music/event/create")
    public String postCreateEventForTimelineOfMusic(@ModelAttribute("eventDTO") EventDTO eventDTO) {
        UUID eventId = eventService.createEventForTimelineOfMusic(eventDTO);
        return "redirect:/management/timeline-of-music/event/edit/" + eventId;
    }

    @GetMapping("/management/timeline-of-music/event/edit/{eventId}")
    public String getEditEventForTimelineOfMusic(@PathVariable String eventId, Model model) {
        Event eventById = eventService.getEventById(UUID.fromString(eventId));
        model.addAttribute("eventDTO", eventService.getEventDTO(eventById));
        model.addAttribute("eraTypes", EraType.values());
        return "management/music/timeline-of-music-edit";
    }

    @PutMapping("/management/timeline-of-music/event/edit/{eventId}")
    public String putEditEventForTimelineOfMusic(@PathVariable String eventId,
                                                 @ModelAttribute("eventDTO") EventDTO eventDTO, Model model) {
        eventService.updateEvent(UUID.fromString(eventId), eventDTO);
        return "redirect:/management/timeline-of-music/event/edit/" + eventId;
    }

    @DeleteMapping("/management/timeline-of-music/event/delete/{eventId}")
    public String deleteEventFromTimelineOfMusic(@PathVariable String eventId) {
        eventService.deleteEventFromTimelineOfMusic(UUID.fromString(eventId));
        return "redirect:/management/timeline-of-music/event/all";
    }
}
