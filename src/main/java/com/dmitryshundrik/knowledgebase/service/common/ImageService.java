package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.repository.common.ImageRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true)
    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Image> getAllSortedByCreatedDesc() {
        return imageRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<Image> getSortedByCreatedDesc(List<Image> imageList) {
        return imageList.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Image getBySlug(String imageSlug) {
        return imageRepository.getBySlug(imageSlug);
    }

    public ImageDTO createImage(ImageDTO imageDTO) {
        Image image = new Image();
        image.setTitle(imageDTO.getTitle().trim());
        image.setSlug(InstantFormatter.instantFormatterYMD(image.getCreated()) + "-" + imageDTO.getSlug());
        image.setDescription(imageDTO.getDescription());
        return getImageDTO(imageRepository.save(image));
    }

    public ImageDTO updateImage(String imageSlug, ImageDTO imageDTO) {
        Image bySlug = getBySlug(imageSlug);
        bySlug.setTitle(imageDTO.getTitle().trim());
        bySlug.setSlug(imageDTO.getSlug().trim());
        bySlug.setDescription(imageDTO.getDescription());
        return getImageDTO(bySlug);
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

    public ImageDTO getImageDTO(Image image) {
        return image != null ? ImageDTO.builder()
                .id(image.getId().toString())
                .created(InstantFormatter.instantFormatterYMDHMS(image.getCreated()))
                .title(image.getTitle())
                .slug(image.getSlug())
                .description(image.getDescription())
                .data(image.getData())
                .build()
                : null;
    }

    public List<ImageDTO> getImageDTOList(List<Image> imageList) {
        return imageList.stream()
                .map(this::getImageDTO).collect(Collectors.toList());
    }

    public ImageDTO createRecipeImage(ImageDTO imageDTO, Recipe recipe) {
        ImageDTO createdImageDTO = createImage(imageDTO);
        recipe.getImageList().add(getBySlug(createdImageDTO.getSlug()));
        return createdImageDTO;
    }

    public void deleteRecipeImage(String imageSlug, Recipe recipe) {
        recipe.getImageList().remove(getBySlug(imageSlug));
        deleteImage(imageSlug);
    }

    public ImageDTO createCocktailImage(ImageDTO imageDTO, Cocktail cocktail) {
        ImageDTO createdImageDTO = createImage(imageDTO);
        cocktail.getImageList().add(getBySlug(createdImageDTO.getSlug()));
        return createdImageDTO;
    }

    public void deleteCocktailImage(String imageSlug, Cocktail cocktail) {
        cocktail.getImageList().remove(getBySlug(imageSlug));
        deleteImage(imageSlug);
    }

    public ImageDTO createPaintingImage(ImageDTO imageDTO, Painting painting) {
        ImageDTO createdImageDTO = createImage(imageDTO);
        painting.setImage(getBySlug(createdImageDTO.getSlug()));
        return createdImageDTO;
    }

    public void deletePaintingImage(String imageSlug, Painting painting) {
        painting.setImage(null);
        deleteImage(imageSlug);
    }
}
