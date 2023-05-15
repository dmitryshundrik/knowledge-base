package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class CocktailManagementController {

    @Autowired
    private CocktailService cocktailService;

    @GetMapping("/management/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAll();
        List<CocktailViewDTO> cocktailViewDTOList = cocktailService.getCocktailViewDTOList(cocktailList);
        model.addAttribute("cocktails", cocktailViewDTOList);
        return "management/gastronomy/cocktail-archive";
    }

    @GetMapping("/management/cocktail/create")
    public String getCocktailCreate(Model model) {
        CocktailCreateEditDTO cocktailCreateEditDTO = new CocktailCreateEditDTO();
        model.addAttribute("cocktailDTO", cocktailCreateEditDTO);
        return "management/gastronomy/cocktail-create";
    }

    @PostMapping("/management/cocktail/create")
    public String postCocktailCreate(@ModelAttribute("cocktailDTO") CocktailCreateEditDTO cocktailDTO) {
        String cocktailDTOSlug = cocktailService.createCocktail(cocktailDTO).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailDTOSlug;
    }

    @GetMapping("/management/cocktail/edit/{cocktailSlug}")
    public String getCocktailEdit(@PathVariable String cocktailSlug, Model model){
        Cocktail bySlug = cocktailService.getBySlug(cocktailSlug);
        CocktailCreateEditDTO cocktailCreateEditDTO = cocktailService.getCocktailCreateEditDTO(bySlug);
        model.addAttribute("cocktailDTO", cocktailCreateEditDTO);
        return "management/gastronomy/cocktail-edit";
    }

    @PutMapping("/management/cocktail/edit/{cocktailSlug}")
    public String putCocktailEdit(@PathVariable String cocktailSlug,
                                  @ModelAttribute("cocktailDTO") CocktailCreateEditDTO cocktailDTO) {
        String cocktailDTOSlug = cocktailService.updateCocktail(cocktailSlug, cocktailDTO).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailDTOSlug;
    }

    @DeleteMapping("management/cocktail/delete/{cocktailSlug}")
    public String deleteCocktailBySlug(@PathVariable String cocktailSlug) {
        cocktailService.deleteCocktail(cocktailSlug);
        return "redirect:/management/cocktail/all";
    }
}
