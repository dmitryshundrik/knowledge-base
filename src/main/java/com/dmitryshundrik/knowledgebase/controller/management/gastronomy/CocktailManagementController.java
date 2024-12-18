package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.CocktailCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.COCKTAIL;
import static com.dmitryshundrik.knowledgebase.util.Constants.COCKTAIL_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class CocktailManagementController {

    private final CocktailService cocktailService;

    @GetMapping("/management/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAllBySortedByCreatedDesc();
        List<CocktailViewDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailList);
        model.addAttribute(COCKTAIL_LIST, cocktailDTOList);
        return "management/gastronomy/cocktail-archive";
    }

    @GetMapping("/management/cocktail/create")
    public String getCocktailCreate(Model model) {
        CocktailCreateEditDTO cocktailCreateEditDTO = new CocktailCreateEditDTO();
        model.addAttribute(COCKTAIL, cocktailCreateEditDTO);
        return "management/gastronomy/cocktail-create";
    }

    @PostMapping("/management/cocktail/create")
    public String postCocktailCreate(@Valid @ModelAttribute(COCKTAIL) CocktailCreateEditDTO cocktailDTO,
                                     BindingResult bindingResult,
                                     Model model) {
        String error = cocktailService.cocktailSlugIsExist(cocktailDTO.getSlug());
        model.addAttribute(SLUG, error);
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            return "management/gastronomy/cocktail-create";
        }
        String cocktailDTOSlug = cocktailService.createCocktail(cocktailDTO).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailDTOSlug;
    }

    @GetMapping("/management/cocktail/edit/{cocktailSlug}")
    public String getCocktailEdit(@PathVariable String cocktailSlug, Model model) {
        Cocktail bySlug = cocktailService.getBySlug(cocktailSlug);
        CocktailCreateEditDTO cocktailCreateEditDTO = cocktailService.getCocktailCreateEditDTO(bySlug);
        model.addAttribute(COCKTAIL, cocktailCreateEditDTO);
        return "management/gastronomy/cocktail-edit";
    }

    @PutMapping("/management/cocktail/edit/{cocktailSlug}")
    public String putCocktailEdit(@PathVariable String cocktailSlug,
                                  @ModelAttribute(COCKTAIL) CocktailCreateEditDTO cocktailDTO) {
        String cocktailDTOSlug = cocktailService.updateCocktail(cocktailSlug, cocktailDTO).getSlug();
        return "redirect:/management/cocktail/edit/" + cocktailDTOSlug;
    }

    @DeleteMapping("management/cocktail/delete/{cocktailSlug}")
    public String deleteCocktailBySlug(@PathVariable String cocktailSlug) {
        cocktailService.deleteCocktail(cocktailSlug);
        return "redirect:/management/cocktail/all";
    }
}
