package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.common.Foundation;
import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.common.dto.FoundationDTO;
import com.dmitryshundrik.knowledgebase.model.common.dto.ResourceDTO;
import com.dmitryshundrik.knowledgebase.service.common.CurrentEventService;
import com.dmitryshundrik.knowledgebase.service.common.EntityActivityService;
import com.dmitryshundrik.knowledgebase.service.common.FoundationService;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.service.spotify.SpotifyIntegrationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final YearInMusicService yearInMusicService;

    private final EntityActivityService entityActivityService;

    private final FoundationService foundationService;

    private final ResourcesService resourcesService;

    private final CurrentEventService currentEventService;

    private final SpotifyIntegrationService spotifyIntegrationService;

    @Getter
    private static final String version = "5.0.6";

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute("yearInMusicList", yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute("latestUpdates", entityActivityService.getLatestActivities());
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
    public String getCurrentSongName() {
        return spotifyIntegrationService.getCurrentSong();
    }
}
