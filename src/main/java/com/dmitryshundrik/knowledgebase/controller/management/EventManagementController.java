package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import com.dmitryshundrik.knowledgebase.service.EventService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("")
public class EventManagementController {

    @Autowired
    private EventService eventService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String getCreateEventForMusician(@PathVariable String musicianSlug, Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("slug", musicianSlug);
        return "management/event-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String postCreateEventForMusician(@PathVariable String musicianSlug,
                                             @Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("slug", musicianSlug);
            return "management/event-create";
        }
        UUID eventId = eventService.createMusicianEventByEventDTO(eventDTO, musicianService.getMusicianBySlug(musicianSlug));
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + eventId;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String getEditMusicianEventById(@PathVariable String musicianSlug, @PathVariable UUID id, Model model) {
        Event eventById = eventService.getEventById(id);
        model.addAttribute("eventDTO", eventService.eventToEventDTO(eventById));
        model.addAttribute("slug", musicianSlug);
        return "management/event-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String putEditMusicianEventById(@PathVariable String musicianSlug,
                                           @PathVariable UUID id,
                                           @ModelAttribute("eventDTO") EventDTO eventDTO) {
        eventService.updateEvent(eventDTO, id);
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + eventDTO.getId();
    }

    @DeleteMapping(("/management/musician/edit/{musicianSlug}/event/delete/{id}"))
    public String deleteMusisianEventById(@PathVariable String musicianSlug, @PathVariable UUID id) {
        eventService.deleteMusicianEventById(musicianService.getMusicianBySlug(musicianSlug), id);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

}
