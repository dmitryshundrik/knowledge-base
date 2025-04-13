package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    @Query(value = "select count(m) from Album m")
    Long getSize();

    @Query("SELECT DISTINCT year FROM Album ORDER BY year")
    List<Integer> findAllYearsFromAlbums();

    Album findAlbumBySlug(String slug);

    List<Album> findAllByYear(Integer year);

    List<Album> findAllByMusician(Musician musician);

    List<Album> findAllByMusicGenresIsContaining(MusicGenre genre);

    List<Album> findFirst20ByOrderByCreatedDesc();

    List<Album> findAllByRatingIsNotNull();

    List<Album> findAllByOrderByCreatedDesc();

    @Query("FROM Album WHERE year > :start AND year < :end AND rating is not null order by rating desc")
    List<Album> findAllByDecadesOrderByRatingDesc(Integer start, Integer end);

    List<Album> findFirst100ByRatingIsNotNullOrderByRatingDesc();
}
