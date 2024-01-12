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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/management")
public class ManagementPageController {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CocktailService cocktailService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private ProseService proseService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private PaintingService paintingService;

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
