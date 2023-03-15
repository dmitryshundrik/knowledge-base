package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.service.music.SOTYListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private SOTYListService sotyListService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("SOTYLists", sotyListService
                .getSOTYListViewDTOList(sotyListService.getAllSOTYLists()));
        return "index";
    }
}
