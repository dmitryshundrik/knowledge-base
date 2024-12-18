package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.dto.music.MusicPeriodCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.MusicPeriodViewDTO;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
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
import javax.validation.Valid;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class MusicPeriodManagementController {

    private final MusicPeriodService musicPeriodService;

    private final MusicianService musicianService;

    @GetMapping("/management/music-period/all")
    public String getAllMusicPeriods(Model model) {
        List<MusicPeriod> musicPeriodList = musicPeriodService.getAllSortedByStart();
        List<MusicPeriodViewDTO> musicPeriodViewDTOList = musicPeriodService.getMusicPeriodViewDTOList(musicPeriodList);
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodViewDTOList);
        return "management/music/music-period-all";
    }

    @GetMapping("/management/music-period/create")
    public String getCreateMusicPeriod(Model model) {
        model.addAttribute(MUSIC_PERIOD, new MusicPeriodCreateEditDTO());
        return "management/music/music-period-create";
    }

    @PostMapping("/management/music-period/create")
    public String postCreateMusicPeriod(@Valid @ModelAttribute(MUSIC_PERIOD) MusicPeriodCreateEditDTO periodDTO, BindingResult bindingResult,
                                        Model model) {
        String error = musicPeriodService.musicPeriodSlugIsExist(periodDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/music/music-period-create";
        }
        String slug = musicPeriodService.createMusicPeriod(periodDTO);
        return "redirect:/management/music-period/edit/" + slug;
    }

    @GetMapping("/management/music-period/edit/{periodSlug}")
    public String getEditMusicPeriod(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriodBySlug = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        model.addAttribute(MUSIC_PERIOD, musicPeriodService.getMusicPeriodCreateEditDTO(musicPeriodBySlug));
        return "management/music/music-period-edit";
    }

    @PutMapping("management/music-period/edit/{periodSlug}")
    public String putEditMusicPeriod(@PathVariable String periodSlug, @ModelAttribute(MUSIC_PERIOD) MusicPeriodCreateEditDTO periodDTO) {
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