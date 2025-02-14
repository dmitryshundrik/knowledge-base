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

    List<Painting> getAllByOrderByCreatedDesc();

    Painting getBySlug(String paintingSlug);

    List<Painting> findFirst20ByOrderByCreatedDesc();

    List<Painting> getAllByArtistAndArtistTopRankNotNull(Artist artist);

    List<Painting> getAllByAndAllTimeTopRankNotNull();

    List<Painting> getAllByArtistOrderByCreatedDesc(Artist artist);

    List<Painting> getAllByArtistOrderByYear2(Artist artist);
}
