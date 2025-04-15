package com.dmitryshundrik.knowledgebase.service.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmArchiveDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

/**
 * Service interface for managing {@link Film} entities.
 * Provides methods for retrieving, creating, updating, and deleting films, as well as handling
 * film-related data such as images and DTO conversions.
 */
public interface FilmService extends BaseService {

    Film getBySlug(String slug);

    List<FilmArchiveDto> getAllFilmArchiveDto();
    List<FilmResponseDto> getTop20BestFilms();
    List<Film> getLatestUpdate();

    Film createFilm(FilmCreateEditDto filmDto);
    Film updateFilm(String filmSlug, FilmCreateEditDto filmDto);
    void deleteFilmBySlug(String filmSlug);

    void updateFilmImageBySlug(String filmSlug, byte[] bytes);
    void deleteFilmImage(String filmSlug);

    FilmCreateEditDto getFilmCreateEditDto(Film film);
}
