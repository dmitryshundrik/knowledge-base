package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management/musician")
public class MusicianManagementController {

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/all")
    public String getAllMusicians(Model model) {
        List<Musician> allMusicians = musicianService.getAllMusicians();
        model.addAttribute("musicians", musicianService.musiciansListToMusiciansViewDTOList(allMusicians));
        return "management/musicianAll";
    }

    @GetMapping("/create")
    public String getCreateMusician(Model model) {
        model.addAttribute("musician", new Musician());
        return "management/musicianCreate";
    }

    @PostMapping("/create")
    public String postCreateMusician(Model model) {

        return "redirect:/management/musician/all";
    }

    @GetMapping("/edit/{slug}")
    public String getEditMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        model.addAttribute("musician", musicianService.musicianToMusicianCreateEditDTO(musicianBySlug));
        model.addAttribute("periods", Period.values());
        model.addAttribute("styles", Style.values());
        return "management/musicianEdit";
    }

    @PutMapping("/edit/{slug}")
    public String putEditMusicianBySlug(@PathVariable String slug, @ModelAttribute("musician") MusicianCreateEditDTO
            musicianCreateEditDTO) {
        musicianService.updateMusician(musicianCreateEditDTO, slug);
        return "redirect:/management/musician/all";
    }

    @DeleteMapping("/delete/{slug}")
    public String deleteMusicianBySlug(@PathVariable String slug, Model model) {
        musicianService.deleteMusicianBySlug(slug);
        model.addAttribute("musicians", musicianService.getAllMusicians());
        return "redirect:/management/musician/all";
    }

}
