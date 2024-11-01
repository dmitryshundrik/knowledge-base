package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
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

    @GetMapping()
    public String getManagementPage(Model model) {
        model.addAttribute("musicianCount", musicianService.getAll().size());
        model.addAttribute("albumCount", albumService.getAll().size());
        model.addAttribute("compositionCount", compositionService.getAll().size());
        model.addAttribute("recipeCount", recipeService.getAll().size());
        model.addAttribute("cocktailCount", cocktailService.getAll().size());
        model.addAttribute("writerCount", writerService.getAll().size());
        model.addAttribute("proseCount", proseService.getAll().size());
        model.addAttribute("quoteCount", quoteService.getAll().size());
        model.addAttribute("artistCount", artistService.getAll().size());
        model.addAttribute("paintingCount", paintingService.getAll().size());
        return "management/management-page";
    }
}
