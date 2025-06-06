package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.core.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.dto.core.PersonEventDto;
import com.dmitryshundrik.knowledgebase.service.core.PersonEventService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import static com.dmitryshundrik.knowledgebase.util.Constants.PERSON_EVENT;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class MusicianEventManagementController {

    private final PersonEventService personEventService;

    private final MusicianService musicianService;

    @GetMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String getCreateEventForMusician(@PathVariable String musicianSlug, Model model) {
        model.addAttribute(PERSON_EVENT, new PersonEventDto());
        model.addAttribute(SLUG, musicianSlug);
        return "management/music/musician-event-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/event/create")
    public String postCreateEventForMusician(@PathVariable String musicianSlug,
                                             @Valid @ModelAttribute(PERSON_EVENT) PersonEventDto personEventDto,
                                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(SLUG, musicianSlug);
            return "management/music/musician-event-create";
        }
        String personEventId = personEventService
                .createMusicianEvent(musicianService.getBySlug(musicianSlug), personEventDto);
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + personEventId;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String getEditMusicianEventById(@PathVariable String musicianSlug, @PathVariable String id, Model model) {
        PersonEvent personEvent = personEventService.getById(id);
        model.addAttribute(PERSON_EVENT, personEventService.getPersonEventDto(personEvent));
        model.addAttribute(SLUG, musicianSlug);
        return "management/music/musician-event-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/event/edit/{id}")
    public String putEditMusicianEventById(@PathVariable String musicianSlug,
                                           @PathVariable String id,
                                           @ModelAttribute(PERSON_EVENT) PersonEventDto personEventDto) {
        String personEventId = personEventService.updatePersonEvent(id, personEventDto).getId();
        return "redirect:/management/musician/edit/" + musicianSlug + "/event/edit/" + personEventId;
    }

    @DeleteMapping(("/management/musician/edit/{musicianSlug}/event/delete/{id}"))
    public String deleteMusicianEventById(@PathVariable String musicianSlug, @PathVariable String id) {
        personEventService.deleteMusicianEvent(musicianService.getBySlug(musicianSlug), id);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }
}
