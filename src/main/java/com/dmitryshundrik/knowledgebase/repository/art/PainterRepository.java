package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PainterRepository extends JpaRepository<Painter, UUID> {

    Painter getBySlug(String painterSlug);

    List<Painter> getAllByOrderByCreatedDesc();

    List<Painter> findFirst20ByOrderByCreatedDesc();

}
