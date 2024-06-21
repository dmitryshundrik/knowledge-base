package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicViewDTO;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.util.MusicianDTOTransformer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class YearInMusicManagementController {

    private final YearInMusicService yearInMusicService;

    private final MusicianService musicianService;

    public YearInMusicManagementController(YearInMusicService yearInMusicService, MusicianService musicianService) {
        this.yearInMusicService = yearInMusicService;
        this.musicianService = musicianService;
    }

    @GetMapping("/management/year-in-music/all")
    public String getAllYearsInMusic(Model model) {
        List<YearInMusic> yearInMusicList = yearInMusicService.getAll();
        List<YearInMusicViewDTO> yearInMusicViewDTOList = yearInMusicService.getYearInMusicViewDTOList(yearInMusicList);
        model.addAttribute("yearInMusicList", yearInMusicViewDTOList);
        return "management/music/year-in-music-all";
    }

    @GetMapping("/management/year-in-music/create")
    public String getCreateYearInMusic(Model model) {
        model.addAttribute("yearInMusicDTO", new YearInMusicCreateEditDTO());
        return "management/music/year-in-music-create";
    }

    @PostMapping("/management/year-in-music/create")
    public String postCreateYearInMusic(@Valid @ModelAttribute("yearInMusicDTO") YearInMusicCreateEditDTO yearInMusicDTO,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "management/music/year-in-music-create";
        }
        String yearInMusicDTOSlug = yearInMusicService.createYearInMusic(yearInMusicDTO).getSlug();
        return "redirect:/management/year-in-music/edit/" + yearInMusicDTOSlug;
    }

    @GetMapping("management/year-in-music/edit/{yearIMSlug}")
    public String getEditYearInMusic(@PathVariable String yearIMSlug, Model model) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        model.addAttribute("yearInMusicDTO", yearInMusicService
                .getYearInMusicCreateEditDTO(yearInMusicBySlug));
        model.addAttribute("musicians", MusicianDTOTransformer
                .getMusicianSelectDTOList(musicianService
                        .getAllMusiciansWithWorksByYear(yearInMusicBySlug.getYear())));
        return "management/music/year-in-music-edit";
    }

    @PutMapping("management/year-in-music/edit/{yearIMSlug}")
    public String putEditYearInMusic(@PathVariable String yearIMSlug,
                                     @ModelAttribute("yearInMusicDTO") YearInMusicCreateEditDTO yearInMusicDTO) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        String yearInMusicDTOSlug = yearInMusicService.updateYearInMusic(yearInMusicBySlug, yearInMusicDTO).getSlug();
        return "redirect:/management/year-in-music/edit/" + yearInMusicDTOSlug;
    }

    @DeleteMapping("management/year-in-music/delete/{yearIMSlug}")
    public String deleteYearInMusicBySlug(@PathVariable String yearIMSlug) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        yearInMusicService.deleteYearInMusic(yearInMusicBySlug);
        return "redirect:/management/year-in-music/all";
    }

}
