package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.entity.art.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    @Query(value = "select count(m) from Painting m")
    Long getSize();

    Painting findBySlug(String paintingSlug);

    List<Painting> findAllByOrderByCreatedDesc();

    List<Painting> findFirst20ByOrderByCreatedDesc();

    List<Painting> findAllByArtistAndArtistTopRankNotNull(Artist artist);

    List<Painting> findAllByAndAllTimeTopRankNotNull();

    List<Painting> findAllByArtistOrderByCreatedDesc(Artist artist);

    List<Painting> findAllByArtistOrderByYear2(Artist artist);
}
