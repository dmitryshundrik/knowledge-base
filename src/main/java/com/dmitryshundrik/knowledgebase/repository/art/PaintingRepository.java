package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    List<Painting> getAllByOrderByCreatedDesc();

    Painting getBySlug(String paintingSlug);

    List<Painting> findFirst20ByOrderByCreatedDesc();

    List<Painting> getAllByPainterAndPainterTopRankNotNull(Painter painter);

    List<Painting> getAllByAndAllTimeTopRankNotNull();

    List<Painting> getAllByPainterOrderByCreatedDesc(Painter painter);

    List<Painting> getAllByPainterOrderByYear2(Painter painter);

}
