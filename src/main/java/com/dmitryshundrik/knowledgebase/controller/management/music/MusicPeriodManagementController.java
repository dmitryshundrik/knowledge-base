package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodViewDTO;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping()
public class MusicPeriodManagementController {

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/music-period/all")
    public String getAllMusicPeriods(Model model) {
        List<MusicPeriod> musicPeriodList = musicPeriodService.getAll();
        List<MusicPeriodViewDTO> musicPeriodViewDTOList = musicPeriodService.getMusicPeriodViewDTOList(musicPeriodList);
        model.addAttribute("musicPeriods", musicPeriodViewDTOList);
        return "management/music/music-period-all";
    }

    @GetMapping("/management/music-period/create")
    public String getCreateMusicPeriod(Model model) {
        model.addAttribute("dto", new MusicPeriodCreateEditDTO());
        return "management/music/music-period-create";
    }

    @PostMapping("/management/music-period/create")
    public String postCreateMusicPeriod(@Valid @ModelAttribute("dto") MusicPeriodCreateEditDTO periodDTO, BindingResult bindingResult,
                                        Model model) {
        String error = musicPeriodService.musicPeriodSlugIsExist(periodDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            return "management/music/music-period-create";
        }
        String slug = musicPeriodService.createMusicPeriod(periodDTO);
        return "redirect:/management/music-period/edit/" + slug;
    }

    @GetMapping("/management/music-period/edit/{periodSlug}")
    public String getEditMusicPeriod(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriodBySlug = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        model.addAttribute("dto", musicPeriodService.getMusicPeriodCreateEditDTO(musicPeriodBySlug));
        return "management/music/music-period-edit";
    }

    @PutMapping("management/music-period/edit/{periodSlug}")
    public String putEditMusicPeriod(@PathVariable String periodSlug, @ModelAttribute("dto") MusicPeriodCreateEditDTO periodDTO) {
        String slug = musicPeriodService.updateMusicPeriod(periodSlug, periodDTO);
        return "redirect:/management/music-period/edit/" + slug;
    }

    @DeleteMapping("/management/music-period/delete/{periodSlug}")
    public String deleteMusicPeriod(@PathVariable String periodSlug) {
        MusicPeriod period = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        musicianService.getAllMusiciansByPeriod(period)
                .forEach(musician -> musician.getMusicPeriods().remove(period));
        musicPeriodService.deleteMusicPeriod(period);
        return "redirect:/management/music-period/all/";
    }

}