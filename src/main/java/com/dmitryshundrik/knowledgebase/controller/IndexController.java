package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.common.Foundation;
import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.common.dto.FoundationDTO;
import com.dmitryshundrik.knowledgebase.model.common.dto.ResourceDTO;
import com.dmitryshundrik.knowledgebase.service.common.CurrentEventService;
import com.dmitryshundrik.knowledgebase.service.common.EntityUpdateInfoService;
import com.dmitryshundrik.knowledgebase.service.common.FoundationService;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.service.spotify.SpotifyIntegrationService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private YearInMusicService yearInMusicService;

    @Autowired
    private EntityUpdateInfoService entityUpdateInfoService;

    @Autowired
    private FoundationService foundationService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private CurrentEventService currentEventService;

    @Autowired
    private SpotifyIntegrationService spotifyIntegrationService;

    @Getter
    private static String version = "4.1.0";

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("yearInMusicList", yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute("latestUpdates", entityUpdateInfoService.getLatestUpdates());
        model.addAttribute("currentEvents", currentEventService.getCurrentEvents());
        return "index";
    }

    @GetMapping("/charity")
    public String getCharity(Model model) {
        List<Foundation> foundationList = foundationService.getAllSortedByCreated();
        List<FoundationDTO> foundationDTOList = foundationService.getFoundationDTOList(foundationList);
        model.addAttribute("foundations", foundationDTOList);
        return "charity";
    }

    @GetMapping("/resources")
    public String getResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllSortedByCreated();
        List<ResourceDTO> resourceDTOList = resourcesService.getResourceDTOList(resourceList);
        model.addAttribute("resources", resourceDTOList);
        return "resources";
    }

    @PostMapping("/current-song")
    @ResponseBody
    public String getCurrentSongName() throws IOException, InterruptedException {
        return spotifyIntegrationService.getCurrentSong();
    }

}
