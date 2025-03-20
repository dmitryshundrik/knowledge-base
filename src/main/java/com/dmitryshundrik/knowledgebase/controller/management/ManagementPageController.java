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
        model.addAttribute("musicianCount", musicianService.getMusicianRepositorySize());
        model.addAttribute("albumCount", albumService.getAlbumRepositorySize());
        model.addAttribute("compositionCount", compositionService.getCompositionRepositorySize());
        model.addAttribute("recipeCount", recipeService.getRecipeRepositorySize());
        model.addAttribute("cocktailCount", cocktailService.getCocktailRepositorySize());
        model.addAttribute("writerCount", writerService.getWriterRepositorySize());
        model.addAttribute("proseCount", proseService.getProseRepositorySize());
        model.addAttribute("quoteCount", quoteService.getQuoteRepositorySize());
        model.addAttribute("artistCount", artistService.getArtistRepositorySize());
        model.addAttribute("paintingCount", paintingService.getPaintingRepositorySize());
        model.addAttribute("filmCount", filmService.getFilmRepositorySize());
        model.addAttribute("criticsListCount", criticsListService.getCriticsListRepositorySize());
        return "management/management-page";
    }
}
