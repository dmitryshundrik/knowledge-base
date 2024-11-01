package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    List<Painting> getAllByOrderByCreatedDesc();

    Painting getBySlug(String paintingSlug);

    List<Painting> findFirst20ByOrderByCreatedDesc();

    List<Painting> getAllByArtistAndArtistTopRankNotNull(Artist artist);

    List<Painting> getAllByAndAllTimeTopRankNotNull();

    List<Painting> getAllByArtistOrderByCreatedDesc(Artist artist);

    List<Painting> getAllByArtistOrderByYear2(Artist artist);
}
