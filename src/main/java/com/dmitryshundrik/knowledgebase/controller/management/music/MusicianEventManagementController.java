package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.dto.PersonEventDTO;
import com.dmitryshundrik.knowledgebase.service.common.PersonEventService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;

@Controller
public class MusicianEventManagementController {

    private final PersonEventService personEventService;

    private final MusicianService musicianService;

    public MusicianEventManagementController(PersonEventService personEventService, MusicianService musicianService) {
        this.personEventService = personEventService;
        this.musicianService = musicianService;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String getCreateEventForMusician(@PathVariable String musicianSlug, Model model) {
        model.addAttribute("personEventDTO", new PersonEventDTO());
        model.addAttribute("slug", musicianSlug);
        return "management/music/musician-event-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String postCreateEventForMusician(@PathVariable String musicianSlug,
                                             @Valid @ModelAttribute("personEventDTO") PersonEventDTO personEventDTO,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("slug", musicianSlug);
            return "management/music/musician-event-create";
        }
        String personEventId = personEventService
                .createMusicianEvent(musicianService.getMusicianBySlug(musicianSlug), personEventDTO);
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + personEventId;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String getEditMusicianEventById(@PathVariable String musicianSlug, @PathVariable String id, Model model) {
        PersonEvent personEventById = personEventService.getPersonEventById(id);
        model.addAttribute("personEventDTO", personEventService.getPersonEventDTO(personEventById));
        model.addAttribute("slug", musicianSlug);
        return "management/music/musician-event-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String putEditMusicianEventById(@PathVariable String musicianSlug,
                                           @PathVariable String id,
                                           @ModelAttribute("personEventDTO") PersonEventDTO personEventDTO) {
        String personEventId = personEventService.updatePersonEvent(id, personEventDTO).getId();
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + personEventId;
    }

    @DeleteMapping(("/management/musician/edit/{musicianSlug}/event/delete/{id}"))
    public String deleteMusicianEventById(@PathVariable String musicianSlug, @PathVariable String id) {
        personEventService.deleteMusicianEventById(musicianService.getMusicianBySlug(musicianSlug), id);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }
}
