package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicViewDto;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
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
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_IN_MUSIC;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_IN_MUSIC_LIST;

@Controller
@RequiredArgsConstructor
public class YearInMusicManagementController {

    private final YearInMusicService yearInMusicService;

    private final MusicianService musicianService;

    @GetMapping("/management/year-in-music/all")
    public String getAllYearsInMusic(Model model) {
        List<YearInMusic> yearInMusicList = yearInMusicService.getAll();
        List<YearInMusicViewDto> yearInMusicDtoList = yearInMusicService.getYearInMusicViewDtoList(yearInMusicList);
        model.addAttribute(YEAR_IN_MUSIC_LIST, yearInMusicDtoList);
        return "management/music/year-in-music-all";
    }

    @GetMapping("/management/year-in-music/create")
    public String getCreateYearInMusic(Model model) {
        model.addAttribute(YEAR_IN_MUSIC, new YearInMusicCreateEditDto());
        return "management/music/year-in-music-create";
    }

    @PostMapping("/management/year-in-music/create")
    public String postCreateYearInMusic(@Valid @ModelAttribute(YEAR_IN_MUSIC) YearInMusicCreateEditDto yearInMusicDto,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "management/music/year-in-music-create";
        }
        String yearInMusicDTOSlug = yearInMusicService.createYearInMusic(yearInMusicDto).getSlug();
        return "redirect:/management/year-in-music/edit/" + yearInMusicDTOSlug;
    }

    @GetMapping("management/year-in-music/edit/{yearIMSlug}")
    public String getEditYearInMusic(@PathVariable String yearIMSlug, Model model) {
        YearInMusic yearInMusicBySlug = yearInMusicService.getBySlug(yearIMSlug);
        model.addAttribute(YEAR_IN_MUSIC, yearInMusicService
                .getYearInMusicCreateEditDto(yearInMusicBySlug));
        model.addAttribute(MUSICIAN_LIST, musicianService
                .getAllActiveMusicianSelectDtoByYear(yearInMusicBySlug.getYear()));
        return "management/music/year-in-music-edit";
    }

    @PutMapping("management/year-in-music/edit/{yearIMSlug}")
    public String putEditYearInMusic(@PathVariable String yearIMSlug,
                                     @ModelAttribute(YEAR_IN_MUSIC) YearInMusicCreateEditDto yearInMusicDto) {
        YearInMusic yearInMusic = yearInMusicService.getBySlug(yearIMSlug);
        String yearInMusicDtoSlug = yearInMusicService.updateYearInMusic(yearInMusic, yearInMusicDto).getSlug();
        return "redirect:/management/year-in-music/edit/" + yearInMusicDtoSlug;
    }

    @DeleteMapping("management/year-in-music/delete/{yearIMSlug}")
    public String deleteYearInMusicBySlug(@PathVariable String yearIMSlug) {
        YearInMusic yearInMusic = yearInMusicService.getBySlug(yearIMSlug);
        yearInMusicService.deleteYearInMusic(yearInMusic);
        return "redirect:/management/year-in-music/all";
    }
}
