package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    Image getBySlug(String imageSlug);

    List<Image> getAllByOrderByCreatedDesc();

}
