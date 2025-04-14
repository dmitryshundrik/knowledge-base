package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.dto.common.ImageDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.entity.common.Image;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.repository.common.ImageRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public List<Image> getAllSortedByCreatedDesc() {
        return imageRepository.findAllByOrderByCreatedDesc();
    }

    public List<Image> getSortedByCreatedDesc(List<Image> imageList) {
        return imageList.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .collect(Collectors.toList());
    }

    public Image getBySlug(String imageSlug) {
        return imageRepository.findBySlug(imageSlug);
    }

    public ImageDto createImage(ImageDto imageDto) {
        Image image = new Image();
        image.setCreated(Instant.now());
        image.setTitle(imageDto.getTitle().trim());
        image.setSlug(InstantFormatter.instantFormatterYMD(image.getCreated()) + "-" + imageDto.getSlug());
        image.setDescription(imageDto.getDescription());
        return getImageDto(imageRepository.save(image));
    }

    public ImageDto updateImage(String imageSlug, ImageDto imageDto) {
        Image bySlug = getBySlug(imageSlug);
        bySlug.setTitle(imageDto.getTitle().trim());
        bySlug.setSlug(imageDto.getSlug().trim());
        bySlug.setDescription(imageDto.getDescription());
        return getImageDto(bySlug);
    }

    public void deleteImage(String imageSlug) {
        Image bySlug = getBySlug(imageSlug);
        imageRepository.delete(bySlug);
    }

    public void uploadFile(String imageSlug, byte[] bytes) {
        Image bySlug = getBySlug(imageSlug);
        if (bytes.length != 0) {
            bySlug.setData(new String(bytes));
        }
    }

    public void deleteFile(String imageSlug) {
        Image bySlug = getBySlug(imageSlug);
        bySlug.setData("");
    }

    public ImageDto getImageDto(Image image) {
        return image != null ? ImageDto.builder()
                .id(image.getId().toString())
                .created(InstantFormatter.instantFormatterYMDHMS(image.getCreated()))
                .title(image.getTitle())
                .slug(image.getSlug())
                .description(image.getDescription())
                .data(image.getData())
                .build()
                : null;
    }

    public List<ImageDto> getImageDtoList(List<Image> imageList) {
        return imageList.stream()
                .map(this::getImageDto).collect(Collectors.toList());
    }

    public ImageDto createRecipeImage(ImageDto imageDTO, Recipe recipe) {
        ImageDto createdImageDto = createImage(imageDTO);
        recipe.getImageList().add(getBySlug(createdImageDto.getSlug()));
        return createdImageDto;
    }

    public void deleteRecipeImage(String imageSlug, Recipe recipe) {
        recipe.getImageList().remove(getBySlug(imageSlug));
        deleteImage(imageSlug);
    }

    public ImageDto createCocktailImage(ImageDto imageDTO, Cocktail cocktail) {
        ImageDto createdImageDto = createImage(imageDTO);
        cocktail.getImageList().add(getBySlug(createdImageDto.getSlug()));
        return createdImageDto;
    }

    public void deleteCocktailImage(String imageSlug, Cocktail cocktail) {
        cocktail.getImageList().remove(getBySlug(imageSlug));
        deleteImage(imageSlug);
    }

    public ImageDto createPaintingImage(ImageDto imageDTO, Painting painting) {
        ImageDto createdImageDto = createImage(imageDTO);
        painting.setImage(getBySlug(createdImageDto.getSlug()));
        return createdImageDto;
    }

    public void deletePaintingImage(String imageSlug, Painting painting) {
        painting.setImage(null);
        deleteImage(imageSlug);
    }
}
