package com.dmitryshundrik.knowledgebase.controller.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.service.cinema.CriticsListService;
import com.dmitryshundrik.knowledgebase.service.cinema.FilmService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.CRITICS_LIST_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.FILM_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;

@Controller
@RequestMapping("/cinema")
@RequiredArgsConstructor
public class CinemaPageController {

    private final FilmService filmService;

    private final CriticsListService criticsListService;

    private final ResourcesService resourcesService;

    @GetMapping
    public String getCinemaPage() {
        return "cinema/cinema-page";
    }

    @GetMapping("/film/top20")
    public String getTop20BestFilms(Model model) {
        List<FilmResponseDto> top20BestFilms = filmService.getTop20BestFilms();
        model.addAttribute(FILM_LIST, top20BestFilms);
        return "cinema/film-top20";
    }

    @GetMapping("/film/top20-overrated")
    public String getTop20OverratedFilms(Model model) {
        return "cinema/film-top20-overrated";
    }

    @GetMapping("/international-critics")
    public String getInternationalCritics(Model model) {
        List<CriticsListResponseDto> allCriticsList = criticsListService.getAllCriticsList();
        model.addAttribute(CRITICS_LIST_LIST, allCriticsList);
        return "cinema/international-critics";
    }

    @GetMapping("/cinema-resources")
    public String getCinemaResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.CINEMA);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "cinema/cinema-resources";
    }
}
