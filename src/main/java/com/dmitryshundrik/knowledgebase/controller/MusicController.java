package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
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
        return "music";
    }

    @GetMapping("/best-songs-{url}")
    public String getSOTYListByYear(@PathVariable String url, Model model) {
        SOTYList sotyListByUrl = musicService.findSOTYListByUrl(url);
        model.addAttribute("SOTYList", sotyListByUrl);
        model.addAttribute("compositionList", musicService.SOTYListToCompositionList(sotyListByUrl));
        return "SOTYList";
    }
}
