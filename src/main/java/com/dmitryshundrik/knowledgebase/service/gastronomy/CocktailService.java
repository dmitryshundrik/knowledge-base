package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.CocktailRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    public Cocktail getBySlug(String cocktailSLug) {
        return cocktailRepository.findBySlug(cocktailSLug);
    }

    public CocktailViewDTO createCocktail(CocktailCreateEditDTO cocktailDTO) {
        Cocktail cocktail = new Cocktail();
        cocktail.setCreated(Instant.now());
        setFieldsFromDTO(cocktail, cocktailDTO);
        return getCocktailViewDTO(cocktailRepository.save(cocktail));
    }

    public CocktailViewDTO updateCocktail(String cocktailSlug, CocktailCreateEditDTO cocktailDTO) {
        Cocktail bySlug = getBySlug(cocktailSlug);
        setFieldsFromDTO(bySlug, cocktailDTO);
        return getCocktailViewDTO(bySlug);
    }

    public void deleteCocktail(String cocktailSlug) {
        Cocktail bySlug = getBySlug(cocktailSlug);
        cocktailRepository.delete(bySlug);
    }

    public CocktailViewDTO getCocktailViewDTO(Cocktail cocktail) {
        return CocktailViewDTO.builder()
                .created(Formatter.instantFormatterYMD(cocktail.getCreated()))
                .slug(cocktail.getSlug())
                .title(cocktail.getTitle())
                .about(cocktail.getAbout())
                .ingredients(cocktail.getIngredients())
                .method(cocktail.getMethod())
                .build();
    }

    public List<CocktailViewDTO> getCocktailViewDTOList(List<Cocktail> cocktailList) {
        return cocktailList.stream().map(this::getCocktailViewDTO).collect(Collectors.toList());
    }

    public CocktailCreateEditDTO getCocktailCreateEditDTO(Cocktail cocktail) {
        return CocktailCreateEditDTO.builder()
                .created(cocktail.getCreated())
                .slug(cocktail.getSlug())
                .title(cocktail.getTitle())
                .about(cocktail.getAbout())
                .ingredients(cocktail.getIngredients())
                .method(cocktail.getMethod())
                .build();
    }

    public void setFieldsFromDTO(Cocktail cocktail, CocktailCreateEditDTO cocktailDTO) {
        cocktail.setTitle(cocktailDTO.getTitle());
        cocktail.setSlug(cocktailDTO.getSlug());
        cocktail.setAbout(cocktailDTO.getAbout());
        cocktail.setIngredients(cocktailDTO.getIngredients());
        cocktail.setMethod(cocktailDTO.getMethod());
    }

    public String cocktailSlugIsExist(String cocktailSlug) {
        String message = "";
        if (getBySlug(cocktailSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

    public List<Cocktail> getLatestUpdate() {
        return cocktailRepository.findFirst10ByOrderByCreated();
    }

}
