package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    Composition getCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> getAllByMusicGenresIsContaining(MusicGenre musicGenre);

    Integer countAllByMusicGenresIsContaining(MusicGenre musicGenre);

    List<Composition> getAllByYearAndYearEndRankNotNull(Integer year);

    List<Composition> findFirst10ByOrderByCreatedDesc();

    @Query("from Composition composition where composition.musician.slug = ?1")
    List<Composition> getAllByMusician(String musicianSlug);

    @Query("from Composition composition where composition.musician.slug = ?1 and composition.rating is not null")
    List<Composition> getAllByMusicianWithRating(String musicianSlug);

    List<Composition> getAllByOrderByCreatedDesc();

    @Query("SELECT DISTINCT year FROM Composition ORDER BY year")
    List<Integer> getAllYearsFromCompositions();

    List<Composition> getAllByYear(Integer year);

}
