package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    Optional<Composition> findBySlug(String slug);

    List<Composition> findAllByOrderByCreatedDesc();

    List<Composition> findAllByMusicGenresIsContaining(MusicGenre musicGenre);

    List<Composition> findAllByYearAndYearEndRankNotNull(Integer year);

    List<Composition> findAllByMusicianId(UUID musicianId, Sort sort);

    List<Composition> findAllByYear(Integer year);

    List<Composition> findFirst20ByOrderByCreatedDesc();

    List<Composition> findByMusicianIn(List<Musician> musicians);

    @Query("SELECT DISTINCT new com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto(" +
            "c.created, c.title, c.musician.nickName, c.musician.slug, c.year, c.rating) " +
            "FROM Composition c " +
            "JOIN c.musicGenres g " +
            "WHERE g.musicGenreType = :musicGenreType AND c.rating IS NOT NULL " +
            "ORDER BY c.rating DESC NULLS LAST " +
            "LIMIT 100")
    List<CompositionSimpleDto> findTop100BestCompositionSimpleDtoOrderByRating(@Param("musicGenreType") MusicGenreType musicGenreType);

    void deleteBySlug(String slug);

    @Query("SELECT DISTINCT year " +
            "FROM Composition " +
            "ORDER BY year")
    List<Integer> getAllYearsFromCompositions();

    @Query(value = "select count(m) from Composition m")
    Long getSize();
}
