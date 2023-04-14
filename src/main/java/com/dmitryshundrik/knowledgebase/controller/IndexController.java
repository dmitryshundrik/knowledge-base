package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.service.common.EntityUpdateInfoService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private YearInMusicService yearInMusicService;

    @Autowired
    private EntityUpdateInfoService entityUpdateInfoService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("yearInMusicList", yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute("latestUpdates", entityUpdateInfoService.getAll());
        return "index";
    }
}
