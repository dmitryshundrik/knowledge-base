package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    @Query("SELECT DISTINCT year " +
            "FROM Composition " +
            "ORDER BY year")
    List<Integer> getAllYearsFromCompositions();

    Composition findCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> findByMusicianIn(List<Musician> musicians);

    List<Composition> findAllByMusicGenresIsContaining(MusicGenre musicGenre);

    @Query("SELECT DISTINCT new com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto(" +
            "c.created, c.title, c.musician.nickName, c.musician.slug, c.year, c.rating) " +
            "FROM Composition c " +
            "JOIN c.musicGenres g " +
            "WHERE g.musicGenreType = :musicGenreType AND c.rating IS NOT NULL " +
            "ORDER BY c.rating DESC " +
            "LIMIT 100")
    List<CompositionSimpleDto> findTop100BestCompositionSimpleDtoOrderByRating(@Param("musicGenreType") MusicGenreType musicGenreType);

    List<Composition> findAllByYearAndYearEndRankNotNull(Integer year);

    List<Composition> findFirst20ByOrderByCreatedDesc();

    @Query("FROM Composition composition " +
            "WHERE composition.musician.slug = ?1 " +
            "AND composition.essentialCompositionsRank IS NOT NULL " +
            "ORDER BY composition.essentialCompositionsRank")
    List<Composition> findAllByMusicianAndEssentialRankNotNull(String musicianSlug);

    List<Composition> findAllByOrderByCreatedDesc();

    List<Composition> findAllByYear(Integer year);

    List<Composition> findAllByMusicianId(UUID musicianId);

    List<Composition> findAllByMusicianIdOrderByCatalogNumber(UUID musicianId);

    List<Composition> findAllByMusicianIdOrderByYear(UUID musicianId);

    List<Composition> findAllByMusicianIdOrderByRating(UUID musicianId);

    List<Composition> findAllByMusicianIdOrderByCreated(UUID musicianId);

    @Query(value = "select count(m) from Composition m")
    Long getSize();
}
