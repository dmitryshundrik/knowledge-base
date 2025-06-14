package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlbumRepository extends JpaRepository<Album, UUID> {

    Optional<Album> findBySlug(String slug);

    List<Album> findAllByYear(Integer year);

    List<Album> findAllByMusician(Musician musician);

    List<Album> findAllByMusicGenresIsContaining(MusicGenre genre);

    List<Album> findFirst20ByOrderByCreatedDesc();

    List<Album> findAllByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto(a.created, a.title, " +
            "a.musician.nickName, a.musician.slug, a.year, a.rating) " +
            "FROM Album a " +
            "ORDER BY a.created DESC")
    List<AlbumSimpleDto> findAllSimpleDtoOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto(a.created, a.title, " +
            "a.musician.nickName, a.musician.slug, a.year, a.rating) " +
            "FROM Album a " +
            "WHERE a.year = :year " +
            "ORDER BY a.rating DESC NULLS LAST")
    List<AlbumSimpleDto> findAllSimpleDtoByYearOrderByRating(@Param("year") Integer year);

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto(a.created, a.title, " +
            "a.musician.nickName, a.musician.slug, a.year, a.rating) " +
            "FROM Album a " +
            "WHERE a.year > :start AND a.year < :end " +
            "ORDER BY a.rating DESC NULLS LAST")
    List<AlbumSimpleDto> findAllSimpleDtoByDecadeOrderByRating(Integer start, Integer end);

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto(a.created, a.title, " +
            "a.musician.nickName, a.musician.slug, a.year, a.rating) " +
            "FROM Album a " +
            "WHERE a.rating IS NOT NULL " +
            "ORDER BY a.rating DESC NULLS LAST " +
            "LIMIT 100")
    List<AlbumSimpleDto> findTop100BestSimpleDtoOrderByRating();

    @Query("SELECT DISTINCT year " +
            "FROM Album " +
            "ORDER BY year")
    List<Integer> findAllYearsFromAlbums();

    @Query(value = "select count(m) from Album m")
    Long getSize();
}
