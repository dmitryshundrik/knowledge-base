package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.repository.common.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageService {


    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAll() {
        return imageRepository.findAll();
    }

    public List<Image> getAllSortedByCreated() {
        return getAll().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .collect(Collectors.toList());
    }

    public Image getBySlug(String imageSlug) {
        return imageRepository.getBySlug(imageSlug);
    }

    public Image createImage(Image image) {
        image.setCreated(Instant.now());
        return imageRepository.save(image);
    }

    public Image updateImage(String imageSlug, Image imageDTO) {
        Image bySlug = getBySlug(imageSlug);
        bySlug.setTitle(imageDTO.getTitle());
        bySlug.setSlug(imageDTO.getSlug());
        bySlug.setDescription(imageDTO.getDescription());
        return bySlug;
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

}
