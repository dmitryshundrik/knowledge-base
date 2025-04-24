package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    @Query("SELECT DISTINCT year FROM Composition ORDER BY year")
    List<Integer> getAllYearsFromCompositions();

    Composition findCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> findAllByMusicGenresIsContaining(MusicGenre musicGenre);

    @Query(nativeQuery = true, value = "select distinct composition.*  from composition " +
            "inner join composition_music_genres " +
            "on composition.id = composition_music_genres.composition_id " +
            "inner join music_genre\n" +
            "on music_genre.id = composition_music_genres.music_genres_id " +
            "where music_genre.music_genre_type = ?1 and composition.rating is not null " +
            "order by composition.rating desc limit ?2")
    List<Composition> findAllByMusicGenresIsContainingAndRatingNotNull(String musicGenreType, Integer limit);

    List<Composition> findAllByYearAndYearEndRankNotNull(Integer year);

    List<Composition> findFirst20ByOrderByCreatedDesc();

    @Query("from Composition composition where composition.musician.slug = ?1 " +
            "and composition.essentialCompositionsRank is not null order by composition.essentialCompositionsRank")
    List<Composition> findAllByMusicianAndEssentialRankNotNull(String musicianSlug);

    @Query("from Composition composition where composition.musician.slug = ?1 and composition.rating is not null")
    List<Composition> findAllByMusicianWithRating(String musicianSlug);

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
