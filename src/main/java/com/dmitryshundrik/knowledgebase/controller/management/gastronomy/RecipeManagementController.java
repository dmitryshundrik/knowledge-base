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
        return "management/gastronomy/recipe-archive";
    }

    @GetMapping("/management/recipe/create")
    public String getRecipeCreate(Model model) {
        RecipeCreateEditDTO recipeCreateEditDTO = new RecipeCreateEditDTO();
        model.addAttribute("recipeDTO", recipeCreateEditDTO);
        model.addAttribute("countries", Country.values());
        return "management/gastronomy/recipe-create";
    }

    @PostMapping("/management/recipe/create")
    public String postRecipeCreate(@ModelAttribute("recipeDTO") RecipeCreateEditDTO recipeDTO) {
        String recipeDTOSlug = recipeService.createRecipe(recipeDTO).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDTOSlug;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}")
    public String getRecipeEdit(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeCreateEditDTO recipeCreateEditDTO = recipeService.getRecipeCreateEditDTO(bySlug);
        model.addAttribute("recipeDTO", recipeCreateEditDTO);
        model.addAttribute("countries", Country.values());
        return "management/gastronomy/recipe-edit";
    }

    @PutMapping("/management/recipe/edit/{recipeSlug}")
    public String putRecipeEdit(@PathVariable String recipeSlug,
                                @ModelAttribute("recipeDTO") RecipeCreateEditDTO recipeDTO) {
        String recipeDTOSlug = recipeService.updateRecipe(recipeSlug, recipeDTO).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDTOSlug;
    }

    @DeleteMapping("/management/recipe/delete/{recipeSlug}")
    public String deleteRecipeBySlug(@PathVariable String recipeSlug) {
        recipeService.deleteRecipe(recipeSlug);
        return "redirect:/management/recipe/all";
    }

}
