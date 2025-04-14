package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingArtistProfileDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    Painting findBySlug(String paintingSlug);

    List<Painting> findAllByOrderByCreatedDesc();

    List<Painting> findAllByArtistOrderByYear2Asc(Artist artist);

    List<Painting> findAllByArtistOrderByYear2Desc(Artist artist);

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.art.PaintingArtistProfileDto(FUNCTION('TO_CHAR', p.created, 'DD-MM-YYYY'), " +
            "p.slug, p.title, p.year1, p.year2, p.approximateYears, p.based, p.artistTopRank, p.allTimeTopRank) " +
            "FROM Painting p WHERE p.artist = :artist ORDER BY p.year2 ASC")
    List<PaintingArtistProfileDto> findAllProfileDtoByArtistOrderByYear2Asc(Artist artist);

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.art.PaintingSimpleDto(p.title, p.year2) " +
            "FROM Painting p WHERE p.artist = :artist AND p.artistTopRank IS NOT NULL " +
            "ORDER BY p.artistTopRank ASC")
    List<PaintingSimpleDto> findAllByArtistAndArtistTopRankNotNull(Artist artist);

    List<Painting> findAllByAllTimeTopRankNotNull();

    List<Painting> findFirst20ByOrderByCreatedDesc();

    @Query(value = "select count(m) from Painting m")
    Long getSize();
}
