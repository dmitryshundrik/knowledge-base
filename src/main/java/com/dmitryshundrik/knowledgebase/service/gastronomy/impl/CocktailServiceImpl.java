package com.dmitryshundrik.knowledgebase.service.gastronomy.impl;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
import com.dmitryshundrik.knowledgebase.mapper.gastronomy.CocktailMapper;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailCreateEditDto;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.CocktailRepository;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.COCKTAIL_NOT_FOUND_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class CocktailServiceImpl implements CocktailService {

    private final CocktailRepository cocktailRepository;

    private final CocktailMapper cocktailMapper;

    @Override
    public Cocktail getBySlug(String cocktailSLug) {
        return cocktailRepository.findBySlug(cocktailSLug)
                .orElseThrow(() -> new NotFoundException(COCKTAIL_NOT_FOUND_MESSAGE.formatted(cocktailSLug)));
    }

    @Override
    public List<Cocktail> getAll() {
        return cocktailRepository.findAll();
    }

    @Override
    public List<Cocktail> getAllByOrderByCreatedDesc() {
        return cocktailRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Cocktail> getLatestUpdate() {
        return cocktailRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public Cocktail createCocktail(CocktailCreateEditDto cocktailDto) {
        Cocktail cocktail = cocktailMapper.toCocktail(cocktailDto);
        return cocktailRepository.save(cocktail);
    }

    @Override
    public Cocktail updateCocktail(String cocktailSlug, CocktailCreateEditDto cocktailDto) {
        Cocktail cocktail = getBySlug(cocktailSlug);
        cocktailMapper.updateCocktail(cocktail, cocktailDto);
        return cocktail;
    }

    @Override
    public void deleteCocktail(String cocktailSlug) {
        Cocktail bySlug = getBySlug(cocktailSlug);
        cocktailRepository.delete(bySlug);
    }

    @Override
    public CocktailViewDto getCocktailViewDto(Cocktail cocktail) {
        return cocktailMapper.toCocktailViewDto(new CocktailViewDto(), cocktail);
    }

    @Override
    public List<CocktailViewDto> getCocktailViewDtoList(List<Cocktail> cocktailList) {
        return cocktailList.stream().map(this::getCocktailViewDto).collect(Collectors.toList());
    }

    @Override
    public CocktailCreateEditDto getCocktailCreateEditDto(Cocktail cocktail) {
        return cocktailMapper.toCocktailCreateEditDto(new CocktailCreateEditDto(), cocktail);
    }

    @Override
    public String isSlugExists(String cocktailSlug) {
        String message = "";
        if (cocktailRepository.findBySlug(cocktailSlug).isPresent()) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return cocktailRepository.getSize();
    }
}
