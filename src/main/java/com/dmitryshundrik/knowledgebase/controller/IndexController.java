package com.dmitryshundrik.knowledgebase.controller;

import com.dmitryshundrik.knowledgebase.model.entity.core.Foundation;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.dto.core.FoundationDto;
import com.dmitryshundrik.knowledgebase.model.dto.core.ResourceDto;
import com.dmitryshundrik.knowledgebase.service.client.WeatherService;
import com.dmitryshundrik.knowledgebase.service.core.EntityActivityService;
import com.dmitryshundrik.knowledgebase.service.core.EntityEventService;
import com.dmitryshundrik.knowledgebase.service.core.FoundationService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.service.client.impl.SpotifyPlayerService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.CURRENT_EVENTS;
import static com.dmitryshundrik.knowledgebase.util.Constants.CURRENT_WEATHER;
import static com.dmitryshundrik.knowledgebase.util.Constants.FOUNDATION_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.LATEST_UPDATES;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_IN_MUSIC_LIST;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final YearInMusicService yearInMusicService;

    private final EntityActivityService entityActivityService;

    private final EntityEventService entityEventService;

    private final FoundationService foundationService;

    private final ResourcesService resourcesService;

    private final WeatherService weatherService;

    private final SpotifyPlayerService spotifyPlayerService;

    @GetMapping("/")
    public String getIndex(Model model) {
        model.addAttribute(YEAR_IN_MUSIC_LIST, yearInMusicService.getYearInMusicSimpleDtoList());
        model.addAttribute(LATEST_UPDATES, entityActivityService.getEntityActivities());
        model.addAttribute(CURRENT_EVENTS, entityEventService.getEntityEvents(10));
        model.addAttribute(CURRENT_WEATHER, weatherService.getCurrentWeather());
        return "index";
    }

    @GetMapping("/charity")
    public String getCharity(Model model) {
        List<Foundation> foundationList = foundationService.getAllOrderByCreated();
        List<FoundationDto> foundationDtoList = foundationService.getFoundationDtoList(foundationList);
        model.addAttribute(FOUNDATION_LIST, foundationDtoList);
        return "charity";
    }

    @GetMapping("/resources")
    public String getResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllByResourceType(ResourceType.NEWS_MEDIA);
        List<ResourceDto> resourceDtoList = resourcesService.getResourceDtoList(resourceList);
        model.addAttribute(RESOURCE_LIST, resourceDtoList);
        return "resources";
    }

    @PostMapping("/current-song")
    @ResponseBody
    public String getCurrentSongName() {
        return spotifyPlayerService.getCurrentSong();
    }
}
