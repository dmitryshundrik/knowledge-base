package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.dto.PersonEventDTO;
import com.dmitryshundrik.knowledgebase.service.common.PersonEventService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class MusicianEventManagementController {

    @Autowired
    private PersonEventService personEventService;

    @Autowired
    private MusicianService musicianService;

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
    public String deleteMusisianEventById(@PathVariable String musicianSlug, @PathVariable String id) {
        personEventService.deleteMusicianEventById(musicianService.getMusicianBySlug(musicianSlug), id);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

}
