package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.cinema.CriticsListService;
import com.dmitryshundrik.knowledgebase.service.cinema.FilmService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.impl.MusicianServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementPageController {

    private final MusicianServiceImpl musicianService;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final RecipeService recipeService;

    private final CocktailService cocktailService;

    private final WriterService writerService;

    private final ProseService proseService;

    private final QuoteService quoteService;

    private final ArtistService artistService;

    private final PaintingService paintingService;

    private final FilmService filmService;

    private final CriticsListService criticsListService;

    @GetMapping()
    public String getManagementPage(Model model) {
        model.addAttribute("musicianCount", musicianService.getRepositorySize());
        model.addAttribute("albumCount", albumService.getRepositorySize());
        model.addAttribute("compositionCount", compositionService.getRepositorySize());
        model.addAttribute("recipeCount", recipeService.getRepositorySize());
        model.addAttribute("cocktailCount", cocktailService.getRepositorySize());
        model.addAttribute("writerCount", writerService.getRepositorySize());
        model.addAttribute("proseCount", proseService.getRepositorySize());
        model.addAttribute("quoteCount", quoteService.getRepositorySize());
        model.addAttribute("artistCount", artistService.getRepositorySize());
        model.addAttribute("paintingCount", paintingService.getRepositorySize());
        model.addAttribute("filmCount", filmService.getRepositorySize());
        model.addAttribute("criticsListCount", criticsListService.getRepositorySize());
        return "management/management-page";
    }
}
