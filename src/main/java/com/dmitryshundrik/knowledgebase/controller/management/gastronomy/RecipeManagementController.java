package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class RecipeManagementController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping("/management/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAll();
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeList);
        model.addAttribute("recipes", recipeViewDTOList);
        return "management/gastronomy/recipe-all";
    }

    @GetMapping("/management/recipe/create")
    public String getCreateRecipe(Model model) {
        RecipeCreateEditDTO recipeCreateEditDTO = new RecipeCreateEditDTO();
        model.addAttribute("recipeDTO", recipeCreateEditDTO);
        model.addAttribute("countries", Country.values());
        return "management/gastronomy/recipe-create";
    }

    @PostMapping("/management/recipe/create")
    public String postCreateRecipe(@ModelAttribute("recipeDTO") RecipeCreateEditDTO recipeDTO, Model model) {
        recipeService.createRecipe(recipeDTO);
        return "redirect:/management/recipe/edit/" + recipeDTO.getSlug();
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}")
    public String getEditRecipe(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeCreateEditDTO recipeCreateEditDTO = recipeService.getRecipeCreateEditDTO(bySlug);
        model.addAttribute("recipeDTO", recipeCreateEditDTO);
        model.addAttribute("countries", Country.values());
        return "management/gastronomy/recipe-edit";
    }

    @PutMapping("/management/recipe/edit/{recipeSlug}")
    public String putEditRecipe(@PathVariable String recipeSlug,
                                @ModelAttribute("recipeDTO") RecipeCreateEditDTO recipeDTO) {
        recipeService.updateRecipe(recipeSlug, recipeDTO);
        return "redirect:/management/recipe/edit/" + recipeDTO.getSlug();
    }

    @DeleteMapping("/management/recipe/delete/{recipeSlug}")
    public String deleteRecipeBySlug(@PathVariable String recipeSlug) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        recipeService.deleteRecipe(bySlug);
        return "redirect:/management/recipe/all";
    }

}
