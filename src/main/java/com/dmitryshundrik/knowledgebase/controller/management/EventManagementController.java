package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import com.dmitryshundrik.knowledgebase.service.EventService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("")
public class EventManagementController {

    @Autowired
    private EventService eventService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/musician/edit/{slug}/event/create")
    public String getCreateEventForMusician(@PathVariable String slug, Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("slug", slug);
        return "management/event-create";
    }

    @PostMapping("/management/musician/edit/{slug}/event/create")
    public String postCreateEventForMusician(@PathVariable String slug, @ModelAttribute("eventDTO") EventDTO eventDTO) {
        Musician musician = musicianService.getMusicianBySlug(slug);
        eventService.createMusicianEventByEventDTO(musician, eventDTO);
        return "redirect:/management/musician/edit/" + slug;
    }

    @GetMapping("/management/musician/edit/{slug}/event/edit/{id}")
    public String getEditMusicianEventById(@PathVariable String slug, @PathVariable UUID id, Model model) {
        Event eventById = eventService.getEventById(id);
        model.addAttribute("eventDTO", eventService.eventToEventDTO(eventById));
        model.addAttribute("slug", slug);
        return "management/event-edit";
    }

    @PutMapping("/management/musician/edit/{slug}/event/edit/{id}")
    public String putEditMusicianEventById(@PathVariable String slug, @PathVariable UUID id, @ModelAttribute("eventDTO") EventDTO eventDTO) {
        eventService.updateEvent(eventDTO, id);
        return "redirect:/management/musician/edit/" + slug;
    }

    @DeleteMapping(("/management/musician/edit/{slug}/event/delete/{id}"))
    public String deleteMusisianEventById(@PathVariable String slug, @PathVariable UUID id) {
        eventService.deleteMusicianEventById(id, musicianService.getMusicianBySlug(slug));
        return "redirect:/management/musician/edit/" + slug;
    }

}
