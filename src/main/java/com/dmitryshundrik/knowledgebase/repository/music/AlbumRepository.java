package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    Album getAlbumBySlug(String slug);

    List<Album> getAllByYear(Integer year);

    List<Album> getAllByMusician(Musician musician);

    List<Album> getAllByMusicPeriodsIsContaining(MusicPeriod period);

    List<Album> getAllByMusicGenresIsContaining(MusicGenre genre);

    Integer countAllByMusicGenresIsContaining(MusicGenre genre);

    List<Album> getAllByYearAndYearEndRankNotNull(Integer year);

    List<Album> findFirst10ByOrderByCreated();

    @Query("SELECT DISTINCT year FROM Album ORDER BY year")
    List<Integer> getAllYearsFromAlbums();

}
