package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.common.dto.EventDTO;
import com.dmitryshundrik.knowledgebase.service.common.EventService;
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
public class MusicianEventManagementController {

    @Autowired
    private EventService eventService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String getCreateEventForMusician(@PathVariable String musicianSlug, Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("slug", musicianSlug);
        return "management/music/event-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String postCreateEventForMusician(@PathVariable String musicianSlug,
                                             @Valid @ModelAttribute("eventDTO") EventDTO eventDTO,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("slug", musicianSlug);
            return "management/music/event-create";
        }
        UUID eventId = eventService.createEventForMusician(musicianService.getMusicianBySlug(musicianSlug), eventDTO);
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + eventId;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String getEditMusicianEventById(@PathVariable String musicianSlug, @PathVariable UUID id, Model model) {
        Event eventById = eventService.getEventById(id);
        model.addAttribute("eventDTO", eventService.getEventDTO(eventById));
        model.addAttribute("slug", musicianSlug);
        return "management/music/event-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String putEditMusicianEventById(@PathVariable String musicianSlug,
                                           @PathVariable UUID id,
                                           @ModelAttribute("eventDTO") EventDTO eventDTO) {
        eventService.updateEvent(id, eventDTO);
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + eventDTO.getId();
    }

    @DeleteMapping(("/management/musician/edit/{musicianSlug}/event/delete/{id}"))
    public String deleteMusisianEventById(@PathVariable String musicianSlug, @PathVariable UUID id) {
        eventService.deleteMusicianEventById(musicianService.getMusicianBySlug(musicianSlug), id);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

}
