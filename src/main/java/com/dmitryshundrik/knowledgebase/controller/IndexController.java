package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.entity.common.Foundation;
import com.dmitryshundrik.knowledgebase.entity.common.Resource;
import com.dmitryshundrik.knowledgebase.dto.common.FoundationDTO;
import com.dmitryshundrik.knowledgebase.dto.common.ResourceDTO;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.CURRENT_EVENTS;
import static com.dmitryshundrik.knowledgebase.util.Constants.FOUNDATION_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.LATEST_UPDATES;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_IN_MUSIC_LIST;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final YearInMusicService yearInMusicService;

    private final EntityActivityService entityActivityService;

    private final FoundationService foundationService;

    private final ResourcesService resourcesService;

    private final CurrentEventService currentEventService;

    priv final SpotifyIntegrationService spotifyIntegrationService;

    @Getter
    private static String version = "5.1.0";

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute(YEAR_IN_MUSIC_LIST, yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute(LATEST_UPDATES, entityActivityService.getLatestActivities());
        model.addAttribute(CURRENT_EVENTS, currentEventService.getCurrentEvents());
        return "index";
    }

    @GetMapping("/charity")
    public String getCharity(Model model) {
        List<Foundation> foundationList = foundationService.getAllSortedByCreated();
        List<FoundationDTO> foundationDTOList = foundationService.getFoundationDTOList(foundationList);
        model.addAttribute(FOUNDATION_LIST, foundationDTOList);
        return "charity";
    }

    @GetMapping("/resources")
    public String getResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllSortedByCreated();
        List<ResourceDTO> resourceDTOList = resourcesService.getResourceDTOList(resourceList);
        model.addAttribute(RESOURCE_LIST, resourceDTOList);
        return "resources";
    }

    @PostMapping("/current-song")
    @ResponseBody
    public String getCurrentSongName() {
        return spotifyIntegrationService.getCurrentSong();
    }
}
