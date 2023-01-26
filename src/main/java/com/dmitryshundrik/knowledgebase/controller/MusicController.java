package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
import com.dmitryshundrik.knowledgebase.service.MusicService;
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

    @GetMapping()
    public String getMusicPage(Model model) {
        model.addAttribute("SOTYLists", musicService.findAllSOTYLists());
        model.addAttribute("periods", Period.values());
        model.addAttribute("styles", Style.values());
        model.addAttribute("genres", Genre.values());
        return "music";
    }

    @GetMapping("/best-songs-{slug}")
    public String getSOTYList(@PathVariable String slug, Model model) {
        SOTYList sotyListBySlug = musicService.findSOTYListBySlug(slug);
        model.addAttribute("SOTYList", sotyListBySlug);
        model.addAttribute("compositionList", musicService.SOTYListToCompositionList(sotyListBySlug));

        return "SOTYList";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic() {

        return "timelineOfMusic";
    }

    @GetMapping("/periods/{slug}")
    public String getPeriod(@PathVariable String slug, Model model) {

        return "period";
    }

    @GetMapping("/styles-and-forms/{slug}")
    public String getStyle(@PathVariable String slug, Model model) {

        return "style";
    }

    @GetMapping("/genres/{slug}")
    public String getGenre(@PathVariable String slug, Model model) {
        Genre genre = musicService.getGenreBySlug(slug);
        model.addAttribute("genre", genre);
        model.addAttribute("compositions", musicService.findAllCompositionsByGenre(genre));
        return "genre";
    }
}
