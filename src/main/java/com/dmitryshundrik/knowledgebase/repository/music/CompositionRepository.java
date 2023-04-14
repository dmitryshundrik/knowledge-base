package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
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

    List<Composition> findFirst10ByOrderByCreated();

    @Query("from Composition composition where composition.musician.slug = ?1")
    List<Composition> getAllByMusician(String musicianSlug);

}
