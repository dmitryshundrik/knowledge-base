package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class YearInMusicManagementController {

    @Autowired
    private YearInMusicService yearInMusicService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/year-in-music/all")
    public String getAllYearsInMusic(Model model) {
        model.addAttribute("yearInMusicList", yearInMusicService.getAll());
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
        YearInMusicCreateEditDTO DTOByCreated = yearInMusicService.createYearInMusic(yearInMusicDTO);
        return "redirect:/management/year-in-music/edit/" + DTOByCreated.getSlug();
    }

    @GetMapping("management/year-in-music/edit/{yearIMSlug}")
    public String getEditYearInMusic(@PathVariable String yearIMSlug, Model model) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        model.addAttribute("yearInMusicDTO", yearInMusicService
                .getYearInMusicCreateEditDTO(yearInMusicBySlug));
        model.addAttribute("musicians", musicianService
                .getMusicianSelectDTOList(musicianService
                        .getAllMusiciansWithWorksByYear(yearInMusicBySlug.getYear())));
        return "management/music/year-in-music-edit";
    }

    @PutMapping("management/year-in-music/edit/{yearIMSlug}")
    public String putEditYearInMusic(@PathVariable String yearIMSlug,
                                     @ModelAttribute("albumCreateEditDTO") YearInMusicCreateEditDTO yearInMusicDTO) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        yearInMusicService.updateYearInMusic(yearInMusicBySlug, yearInMusicDTO);
        return "redirect:/management/year-in-music/edit/" + yearInMusicDTO.getSlug();
    }

    @DeleteMapping("management/year-in-music/delete/{yearIMSlug}")
    public String deleteYearInMusicBySlug(@PathVariable String yearIMSlug) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getYearInMusicBySlug(yearIMSlug);
        yearInMusicService.deleteYearInMusic(yearInMusicBySlug);
        return "redirect:/management/album/all";
    }
}
