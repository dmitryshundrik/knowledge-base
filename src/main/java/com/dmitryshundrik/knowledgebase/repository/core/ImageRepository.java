package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    Image findBySlug(String imageSlug);

    List<Image> findAllByOrderByCreatedDesc();
}
