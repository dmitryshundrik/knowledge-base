package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.entity.music.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    @Query(value = "select count(m) from Composition m")
    Long getSize();

    Composition getCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> getAllByMusicGenresIsContaining(MusicGenre musicGenre);

    List<Composition> getAllByYearAndYearEndRankNotNull(Integer year);

    List<Composition> findFirst20ByOrderByCreatedDesc();

    @Query("from Composition composition where composition.musician.slug = ?1 " +
            "and composition.essentialCompositionsRank is not null order by composition.essentialCompositionsRank")
    List<Composition> getAllByMusicianAndEssentialRankNotNull(String musicianSlug);

    @Query("from Composition composition where composition.musician.slug = ?1 and composition.rating is not null")
    List<Composition> getAllByMusicianWithRating(String musicianSlug);

    List<Composition> getAllByOrderByCreatedDesc();

    @Query("SELECT DISTINCT year FROM Composition ORDER BY year")
    List<Integer> getAllYearsFromCompositions();

    List<Composition> getAllByYear(Integer year);

    List<Composition> getAllByMusicianId(UUID musicianId);

    List<Composition> getAllByMusicianIdOrderByCatalogNumber(UUID musicianId);

    List<Composition> getAllByMusicianIdOrderByYear(UUID musicianId);

    List<Composition> getAllByMusicianIdOrderByRating(UUID musicianId);

    List<Composition> getAllByMusicianIdOrderByCreated(UUID musicianId);
}
