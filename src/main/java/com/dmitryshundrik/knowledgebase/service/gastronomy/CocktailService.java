package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailDTO;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.CocktailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CocktailService {

    @Autowired
    private CocktailRepository cocktailRepository;

    public List<Cocktail> getAll() {
        return cocktailRepository.findAll();
    }

    public Cocktail getBySlug(String slug) {
        return cocktailRepository.findBySlug(slug);
    }

    public CocktailDTO getCocktailViewDTO(Cocktail cocktail) {
        return CocktailDTO.builder()
                .created(cocktail.getCreated())
                .slug(cocktail.getSlug())
                .title(cocktail.getTitle())
                .country(cocktail.getCountry())
                .about(cocktail.getAbout())
                .ingredients(cocktail.getIngredients())
                .method(cocktail.getMethod())
                .build();
    }

    public List<CocktailDTO> getCocktailViewDTOList(List<Cocktail> cocktailList) {
        return cocktailList.stream().map(this::getCocktailViewDTO).collect(Collectors.toList());
    }

}
