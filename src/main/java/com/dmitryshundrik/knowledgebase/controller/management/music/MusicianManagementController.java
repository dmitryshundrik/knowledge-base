package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianValidationService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("")
public class MusicianManagementController {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private MusicianValidationService musicianValidationService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping("/management/musician/all")
    public String getAllMusicians(Model model) {
        List<Musician> allMusicians = musicianService.getAllMusicians();
        model.addAttribute("musicianViewDTOList", musicianService.getMusicianViewDTOList(allMusicians));
        return "management/musician-all";
    }

    @GetMapping("/management/musician/create")
    public String getCreateMusician(Model model) {
        MusicianCreateEditDTO musicianDTO = new MusicianCreateEditDTO();
        musicianDTO.setAlbumsSortType(SortType.YEAR);
        musicianDTO.setCompositionsSortType(SortType.YEAR);
        model.addAttribute("musicianCreateEditDTO", musicianDTO);
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        model.addAttribute("sortTypes", SortType.values());
        return "management/musician-create";
    }

    @PostMapping("/management/musician/create")
    public String postCreateMusician(@Valid @ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianCreateEditDTO,
                                     BindingResult bindingResult, Model model) {
        String error = musicianValidationService.musicianSlugIsExist(musicianCreateEditDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            model.addAttribute("musicPeriods", musicPeriodService.getAll());
            model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
            model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
            model.addAttribute("sortTypes", SortType.values());
            return "management/musician-create";
        }
        musicianService.createMusicianByMusicianDTO(musicianCreateEditDTO);
        return "redirect:/management/musician/edit/" + musicianCreateEditDTO.getSlug();
    }

    @GetMapping("/management/musician/edit/{musicianSlug}")
    public String getEditMusicianBySlug(@PathVariable String musicianSlug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(musicianSlug);
        model.addAttribute("musicianCreateEditDTO", musicianService.getMusicianCreateEditDTO(musicianBySlug));
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        model.addAttribute("sortTypes", SortType.values());
        return "management/musician-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}")
    public String putEditMusicianBySlug(@PathVariable String musicianSlug,
                                        @ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianCreateEditDTO) {
        musicianService.updateExistingMusician(musicianCreateEditDTO, musicianSlug);
        return "redirect:/management/musician/edit/" + musicianCreateEditDTO.getSlug();
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/image/upload")
    public String postUploadMusicianImage(@PathVariable String musicianSlug,
                                          @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        musicianService.updateMusicianImageBySlug(musicianSlug, bytes);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/edit/{musicianSlug}/image/delete")
    public String deleteMusicianImage(@PathVariable String musicianSlug) {
        musicianService.deleteMuscianImage(musicianSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/delete/{musicianSlug}")
    public String deleteMusicianBySlug(@PathVariable String musicianSlug) {
        musicianService.deleteMusicianBySlug(musicianSlug);
        return "redirect:/management/musician/all";
    }

}