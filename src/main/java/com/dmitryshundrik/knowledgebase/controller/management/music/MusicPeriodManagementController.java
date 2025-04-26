package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDto;
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
        List<MusicPeriodViewDto> musicPeriodDtoList = musicPeriodService.getMusicPeriodViewDtoList(musicPeriodList);
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodDtoList);
        return "management/music/music-period-all";
    }

    @GetMapping("/management/music-period/create")
    public String getCreateMusicPeriod(Model model) {
        model.addAttribute(MUSIC_PERIOD, new MusicPeriodCreateEditDto());
        return "management/music/music-period-create";
    }

    @PostMapping("/management/music-period/create")
    public String postCreateMusicPeriod(@Valid @ModelAttribute(MUSIC_PERIOD) MusicPeriodCreateEditDto periodDto, BindingResult bindingResult,
                                        Model model) {
        String error = musicPeriodService.musicPeriodSlugIsExist(periodDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/music/music-period-create";
        }
        String slug = musicPeriodService.createMusicPeriod(periodDto);
        return "redirect:/management/music-period/edit/" + slug;
    }

    @GetMapping("/management/music-period/edit/{periodSlug}")
    public String getEditMusicPeriod(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriodBySlug = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        model.addAttribute(MUSIC_PERIOD, musicPeriodService.getMusicPeriodCreateEditDto(musicPeriodBySlug));
        return "management/music/music-period-edit";
    }

    @PutMapping("management/music-period/edit/{periodSlug}")
    public String putEditMusicPeriod(@PathVariable String periodSlug, @ModelAttribute(MUSIC_PERIOD) MusicPeriodCreateEditDto periodDto) {
        String slug = musicPeriodService.updateMusicPeriod(periodSlug, periodDto);
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