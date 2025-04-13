package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    Painting findBySlug(String paintingSlug);

    List<Painting> findAllByOrderByCreatedDesc();

    List<Painting> findAllByArtistOrderByYear2(Artist artist);

    List<Painting> findAllByArtistAndArtistTopRankNotNull(Artist artist);

    List<Painting> findAllByAllTimeTopRankNotNull();

    List<Painting> findFirst20ByOrderByCreatedDesc();

    @Query(value = "select count(m) from Painting m")
    Long getSize();
}
