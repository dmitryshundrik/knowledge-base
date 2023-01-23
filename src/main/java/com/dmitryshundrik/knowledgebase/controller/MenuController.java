package com.dmitryshundrik.knowledgebase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class MenuController {

    @GetMapping("/music")
    public String music() {

        return "music";
    }

    @GetMapping("/gastronomy")
    public String gastronomy() {

        return "gastronomy";
    }

    @GetMapping("/resources")
    public String resources() {

        return "resources";
    }
}
