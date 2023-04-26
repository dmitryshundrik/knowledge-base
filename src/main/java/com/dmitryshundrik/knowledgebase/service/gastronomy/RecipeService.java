package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.RecipeRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    public Recipe getBySlug(String slug) {
        return recipeRepository.findBySlug(slug);
    }

    public void createRecipe(RecipeCreateEditDTO recipeCreateEditDTO) {
        Recipe recipe = new Recipe();
        recipe.setCreated(Instant.now());
        setFieldsFromDTO(recipe, recipeCreateEditDTO);
        recipeRepository.save(recipe);
    }

    public void updateRecipe(String recipeSlug, RecipeCreateEditDTO recipeDTO) {
        Recipe bySlug = getBySlug(recipeSlug);
        setFieldsFromDTO(bySlug, recipeDTO);
    }

    public void deleteRecipe(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public RecipeViewDTO getRecipeViewDTO(Recipe recipe) {
        return RecipeViewDTO.builder()
                .created(Formatter.instantFormatterYMD(recipe.getCreated()))
                .slug(recipe.getSlug())
                .title(recipe.getTitle())
                .country(recipe.getCountry())
                .about(recipe.getAbout())
                .ingredients(recipe.getIngredients())
                .method(recipe.getMethod())
                .build();
    }

    public List<RecipeViewDTO> getRecipeViewDTOList(List<Recipe> recipeList) {
        return recipeList.stream().map(this::getRecipeViewDTO).collect(Collectors.toList());
    }

    public RecipeCreateEditDTO getRecipeCreateEditDTO(Recipe recipe) {
        return RecipeCreateEditDTO.builder()
                .slug(recipe.getSlug())
                .title(recipe.getTitle())
                .country(recipe.getCountry())
                .about(recipe.getAbout())
                .ingredients(recipe.getIngredients())
                .method(recipe.getMethod())
                .build();
    }

    public void setFieldsFromDTO(Recipe recipe, RecipeCreateEditDTO recipeDTO) {
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setSlug(recipeDTO.getSlug());
        recipe.setCountry(recipeDTO.getCountry());
        recipe.setAbout(recipeDTO.getAbout());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setMethod(recipeDTO.getMethod());
    }

}
