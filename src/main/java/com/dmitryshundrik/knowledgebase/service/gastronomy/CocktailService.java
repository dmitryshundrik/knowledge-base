package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.CocktailCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.CocktailRepository;
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
public class CocktailService {

    private final CocktailRepository cocktailRepository;

    private final ImageService imageService;

    @Transactional(readOnly = true)
    public List<Cocktail> getAll() {
        return cocktailRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Cocktail> getAllBySortedByCreatedDesc() {
        return cocktailRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public Cocktail getBySlug(String cocktailSLug) {
        return cocktailRepository.findBySlug(cocktailSLug);
    }

    public CocktailViewDTO createCocktail(CocktailCreateEditDTO cocktailDTO) {
        Cocktail cocktail = new Cocktail();
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
                .created(InstantFormatter.instantFormatterDMY(cocktail.getCreated()))
                .slug(cocktail.getSlug())
                .title(cocktail.getTitle())
                .about(cocktail.getAbout())
                .ingredients(cocktail.getIngredients())
                .method(cocktail.getMethod())
                .imageList(cocktail.getImageList() != null ? imageService
                        .getImageDTOList(imageService
                                .getSortedByCreatedDesc(cocktail.getImageList())) : null)
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
                .imageList(cocktail.getImageList() != null ? imageService
                        .getImageDTOList(imageService
                                .getSortedByCreatedDesc(cocktail.getImageList())) : null)
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
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<Cocktail> getLatestUpdate() {
        return cocktailRepository.findFirst20ByOrderByCreatedDesc();
    }
}
