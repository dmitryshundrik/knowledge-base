package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.service.common.EntityUpdateInfoService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private YearInMusicService yearInMusicService;

    @Autowired
    private EntityUpdateInfoService updateInfoService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("yearInMusicList", yearInMusicService.getAll());
        model.addAttribute("latestUpdates", updateInfoService.getAll());
        return "index";
    }
}