package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.entity.core.Foundation;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.dto.core.FoundationDto;
import com.dmitryshundrik.knowledgebase.model.dto.core.ResourceDTO;
import com.dmitryshundrik.knowledgebase.service.core.CurrentEventService;
import com.dmitryshundrik.knowledgebase.service.core.EntityActivityService;
import com.dmitryshundrik.knowledgebase.service.core.FoundationService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.service.client.impl.SpotifyPlayerService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
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

    private final SpotifyPlayerService spotifyPlayerService;

    @Getter
    private static String version = "6.1.0";

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute(YEAR_IN_MUSIC_LIST, yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute(LATEST_UPDATES, entityActivityService.getLatestActivities());
        model.addAttribute(CURRENT_EVENTS, currentEventService.getCurrentEvents(10));
        return "index";
    }

    @GetMapping("/charity")
    public String getCharity(Model model) {
        List<Foundation> foundationList = foundationService.getAllSortedByCreated();
        List<FoundationDto> foundationDtoList = foundationService.getFoundationDTOList(foundationList);
        model.addAttribute(FOUNDATION_LIST, foundationDtoList);
        return "charity";
    }

    @GetMapping("/resources")
    public String getResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllByResourceType(ResourceType.NEWS_MEDIA);
        List<ResourceDTO> resourceDTOList = resourcesService.getResourceDTOList(resourceList);
        model.addAttribute(RESOURCE_LIST, resourceDTOList);
        return "resources";
    }

    @PostMapping("/current-song")
    @ResponseBody
    public String getCurrentSongName() {
        return spotifyPlayerService.getCurrentSong();
    }
}
