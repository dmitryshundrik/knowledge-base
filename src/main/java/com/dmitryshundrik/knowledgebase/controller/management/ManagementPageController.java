package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.cinema.CriticsListService;
import com.dmitryshundrik.knowledgebase.service.cinema.FilmService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.impl.CocktailServiceImpl;
import com.dmitryshundrik.knowledgebase.service.gastronomy.impl.RecipeServiceImpl;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.literature.impl.ProseServiceImpl;
import com.dmitryshundrik.knowledgebase.service.literature.impl.QuoteService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
@RequiredArgsConstructor
public class ManagementPageController {

    private final MusicianService musicianService;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final RecipeServiceImpl recipeService;

    private final CocktailServiceImpl cocktailService;

    private final WriterService writerService;

    private final ProseServiceImpl proseService;

    private final QuoteService quoteService;

    private final ArtistService artistService;

    private final PaintingService paintingService;

    private final FilmService filmService;

    private final CriticsListService criticsListService;

    @GetMapping()
    public String getManagementPage(Model model) {
        model.addAttribute("musicianCount", musicianService.getMusicianRepositorySize());
        model.addAttribute("albumCount", albumService.getAlbumRepositorySize());
        model.addAttribute("compositionCount", compositionService.getCompositionRepositorySize());
        model.addAttribute("recipeCount", recipeService.getRepositorySize());
        model.addAttribute("cocktailCount", cocktailService.getRepositorySize());
        model.addAttribute("writerCount", writerService.getRepositorySize());
        model.addAttribute("proseCount", proseService.getRepositorySize());
        model.addAttribute("quoteCount", quoteService.getQuoteRepositorySize());
        model.addAttribute("artistCount", artistService.getRepositorySize());
        model.addAttribute("paintingCount", paintingService.getRepositorySize());
        model.addAttribute("filmCount", filmService.getRepositorySize());
        model.addAttribute("criticsListCount", criticsListService.getRepositorySize());
        return "management/management-page";
    }
}
