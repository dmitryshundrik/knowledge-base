package com.dmitryshundrik.knowledgebase.repository.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmArchiveDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface FilmRepository extends JpaRepository<Film, UUID> {

    @Query(value = "select count(m) from Film m")
    Long getSize();

    Film findBySlug(String slug);

    List<Film> findFirst20ByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmArchiveDto(film.created, film.slug, film.title, " +
            "film.director, film.starring, film.year, film.rating, film.yearRank, film.allTimeRank) " +
            "FROM Film film ORDER BY film.created DESC")
    List<FilmArchiveDto> findAllFilmArchiveDtoOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto(film.slug, film.title, film.director, film.starring, " +
            "film.year, film.rating, film.yearRank, film.allTimeRank, film.synopsis, film.image) FROM Film film WHERE film.allTimeRank IS NOT null " +
            "ORDER BY film.allTimeRank ASC")
    List<FilmResponseDto> findAllByAllTimeRankNotNullAndOrderByAllTimeRankAsc();
}
