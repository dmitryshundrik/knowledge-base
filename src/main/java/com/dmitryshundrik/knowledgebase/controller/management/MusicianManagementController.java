package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class MusicianManagementController {

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/musician/all")
    public String getAllMusicians(Model model) {
        List<Musician> allMusicians = musicianService.getAllMusicians();
        model.addAttribute("musicianViewDTOList", musicianService.getMusicianViewDTOList(allMusicians));
        return "management/musician-all";
    }

    @GetMapping("/management/musician/create")
    public String getCreateMusician(Model model) {
        model.addAttribute("musicianCreateEditDTO", new MusicianCreateEditDTO());
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/musician-create";
    }

    @PostMapping("/management/musician/create")
    public String postCreateMusician(@ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianCreateEditDTO) {
        musicianService.createMusicianByMusicianDTO(musicianCreateEditDTO);
        return "redirect:/management/musician/all";
    }

    @GetMapping("/management/musician/edit/{slug}")
    public String getEditMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        model.addAttribute("musicianCreateEditDTO", musicianService.getMusicianCreateEditDTO(musicianBySlug));
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/musician-edit";
    }

    @PutMapping("/management/musician/edit/{slug}")
    public String putEditMusicianBySlug(@PathVariable String slug, @ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianCreateEditDTO) {
        musicianService.updateExistingMusician(musicianCreateEditDTO, slug);
        return "redirect:/management/musician/edit/" + musicianCreateEditDTO.getSlug();
    }

    @PostMapping("/management/musician/edit/{slug}/upload")
    public String postUploadMusicianImage(@PathVariable String slug, @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        musicianService.updateMusicianImageBySlug(slug, bytes);
        return "redirect:/management/musician/edit/" + slug;
    }

    @DeleteMapping("/management/musician/delete/{slug}")
    public String deleteMusicianBySlug(@PathVariable String slug) {
        musicianService.deleteMusicianBySlug(slug);
        return "redirect:/management/musician/all";
    }

}
