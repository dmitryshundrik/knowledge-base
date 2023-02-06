package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.timeline.TimelineType;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
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
    public String getMusicPage(Model model) {
        model.addAttribute("SOTYLists", sotyListService.getAllSOTYLists());
        model.addAttribute("periods", Period.values());
        model.addAttribute("styles", Style.getSortedValues());
        model.addAttribute("genres", Genre.getSortedValues());
        return "music/music";
    }

    @GetMapping("/best-songs-{slug}")
    public String getSOTYList(@PathVariable String slug, Model model) {
        SOTYList sotyListBySlug = sotyListService.getSOTYListBySlug(slug);
        model.addAttribute("SOTYList", sotyListBySlug);
        model.addAttribute("compositions", compositionService.getAllCompositionsBySOTYList(sotyListBySlug));

        return "music/SOTYList";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        model.addAttribute("timeline", timelineService.getTimeline(TimelineType.MUSIC));
        return "music/timelineOfMusic";
    }

    @GetMapping("/musicians")
    public String getAllMusicians(Model model) {
        model.addAttribute("musicians", musicianService.getAllMusicians());
        return "music/musicians";
    }

    @GetMapping("/periods/{slug}")
    public String getPeriod(@PathVariable String slug, Model model) {
        Period periodBySlug = musicService.getPeriodBySlug(slug);
        model.addAttribute("period", periodBySlug);
        model.addAttribute("compostitions", compositionService.getAllCompositionsByPeriod(periodBySlug));
        return "music/period";
    }

    @GetMapping("/styles-and-forms/{slug}")
    public String getStyle(@PathVariable String slug, Model model) {

        return "music/style";
    }

    @GetMapping("/genres/{slug}")
    public String getGenre(@PathVariable String slug, Model model) {
        Genre genreBySlug = musicService.getGenreBySlug(slug);
        model.addAttribute("genre", genreBySlug);
        model.addAttribute("compositions", compositionService.getAllCompositionsByGenre(genreBySlug));
        return "music/genre";
    }
}
