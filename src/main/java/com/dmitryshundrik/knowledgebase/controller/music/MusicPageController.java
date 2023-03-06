package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.service.music.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/music")
public class MusicPageController {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private SOTYListService sotyListService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping()
    public String getMusicPage(Model model) {
        model.addAttribute("SOTYLists", sotyListService.getAllSOTYLists());
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalMusicGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryMusicGenres", musicGenreService.getAllContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getSOTYList(@PathVariable String slug, Model model) {
        SOTYList sotyListBySlug = sotyListService.getSOTYListBySlug(slug);
        model.addAttribute("SOTYList", sotyListBySlug);
        model.addAttribute("compositions", compositionService.getAllCompositionsBySOTYList(sotyListBySlug));
        return "music/soty-list";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        return "music/timeline-of-music";
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(Model model) {
        model.addAttribute("musicians", musicianService.getAllMusicians());
        return "music/musician-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        model.addAttribute("musicianViewDTO", musicianService.getMusicianViewDTO(musicianBySlug));
        return "music/musician";
    }

    @GetMapping("/period/{slug}")
    public String getPeriodBySlug(@PathVariable String slug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getMusicPeriodBySlug(slug);
        model.addAttribute("musicPeriod", musicPeriod);
        model.addAttribute("compostitions", compositionService.getAllCompositionsByPeriod(musicPeriod));
        return "music/period";
    }

    @GetMapping("/genre/{slug}")
    public String getClassicalGenreBySlug(@PathVariable String slug, Model model) {
        MusicGenre musicGenre = musicGenreService.getMusicGenreBySlug(slug);
        model.addAttribute("musicGenre", musicGenre);
        model.addAttribute("compositions", compositionService.getAllCompositionsByMusicGenre(musicGenre));
        return "music/genre";
    }

}
