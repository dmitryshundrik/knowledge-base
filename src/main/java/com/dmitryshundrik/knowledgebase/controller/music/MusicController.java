package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicService;
import com.dmitryshundrik.knowledgebase.service.TimelineService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.SOTYListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private SOTYListService sotyListService;

    @Autowired
    private TimelineService timelineService;

    @GetMapping()
    public String getMainPage(Model model) {
        model.addAttribute("SOTYLists", sotyListService.getAllSOTYLists());
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "music/main-page";
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
        model.addAttribute("timeline", timelineService.getTimeline(TimelineType.MUSIC));
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
        Period periodBySlug = musicService.getPeriodBySlug(slug);
        model.addAttribute("period", periodBySlug);
        model.addAttribute("compostitions", compositionService.getAllCompositionsByPeriod(periodBySlug));
        return "music/period";
    }

    @GetMapping("/academic-genre/{slug}")
    public String getAcademicGenreBySlug(@PathVariable String slug, Model model) {
        AcademicGenre academicGenre = musicService.getAcademicGenreByClug(slug);
        model.addAttribute("genre", academicGenre);
        model.addAttribute("compositions", compositionService.getAllCompositionsByAcademicGenre(academicGenre));
        return "music/genre";
    }

    @GetMapping("/contemporary-genre/{slug}")
    public String getContemporaryGenreBySlug(@PathVariable String slug, Model model) {
        ContemporaryGenre contemporaryGenre = musicService.getContemporaryGenreBySlug(slug);
        model.addAttribute("genre", contemporaryGenre);
        model.addAttribute("compositions", compositionService.getAllCompositionsByContemporaryGenre(contemporaryGenre));
        return "music/genre";
    }
}
