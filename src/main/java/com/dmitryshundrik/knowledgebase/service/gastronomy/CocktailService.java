package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

/**
 * Service interface for managing {@link Cocktail} entities.
 * Provides methods for retrieving, creating, updating, and deleting cocktails, as well as handling
 * cocktail-related data, and DTO conversions.
 */
public interface CocktailService extends BaseService {

    Cocktail getBySlug(String cocktailSLug);

    List<Cocktail> getAll();
    List<Cocktail> getAllBySortedByCreatedDesc();
    List<Cocktail> getLatestUpdate();

    CocktailViewDto createCocktail(CocktailCreateEditDto cocktailDTO);
    CocktailViewDto updateCocktail(String cocktailSlug, CocktailCreateEditDto cocktailDTO);
    void deleteCocktail(String cocktailSlug);

    CocktailViewDto getCocktailViewDto(Cocktail cocktail);
    List<CocktailViewDto> getCocktailViewDtoList(List<Cocktail> cocktailList);
    CocktailCreateEditDto getCocktailCreateEditDto(Cocktail cocktail);
}
