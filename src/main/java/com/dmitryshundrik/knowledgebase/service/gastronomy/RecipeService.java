package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.util.enums.Country;
import com.dmitryshundrik.knowledgebase.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.RecipeCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.RecipeRepository;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllBySortedByCreatedDesc() {
        return recipeRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<Recipe> getAllByCountry(Country country) {
        return recipeRepository.findAllByCountry(country);
    }

    @Transactional(readOnly = true)
    public Recipe getBySlug(String recipeSlug) {
        return recipeRepository.findBySlug(recipeSlug);
    }

    public RecipeViewDTO createRecipe(RecipeCreateEditDTO recipeCreateEditDTO) {
        Recipe recipe = new Recipe();
        setFieldsFromDTO(recipe, recipeCreateEditDTO);
        return getRecipeViewDTO(recipeRepository.save(recipe));
    }

    public RecipeViewDTO updateRecipe(String recipeSlug, RecipeCreateEditDTO recipeDTO) {
        Recipe bySlug = getBySlug(recipeSlug);
        setFieldsFromDTO(bySlug, recipeDTO);
        return getRecipeViewDTO(bySlug);
    }

    public void deleteRecipe(String recipeSlug) {
        Recipe bySlug = getBySlug(recipeSlug);
        recipeRepository.delete(bySlug);
    }

    public RecipeViewDTO getRecipeViewDTO(Recipe recipe) {
        return RecipeViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(recipe.getCreated()))
                .slug(recipe.getSlug())
                .title(recipe.getTitle())
                .country(recipe.getCountry())
                .about(recipe.getAbout())
                .ingredients(recipe.getIngredients())
                .method(recipe.getMethod())
                .imageList(recipe.getImageList() != null ? imageService
                        .getImageDTOList(imageService
                                .getSortedByCreatedDesc(recipe.getImageList())) : null)
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
                .imageList(recipe.getImageList() != null ? imageService
                        .getImageDTOList(imageService
                                .getSortedByCreatedDesc(recipe.getImageList())) : null)
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

    public String recipeSlugIsExist(String recipeSlug) {
        String message = "";
        if (getBySlug(recipeSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<Recipe> getLatestUpdate() {
        return recipeRepository.findFirst20ByOrderByCreatedDesc();
    }
}
