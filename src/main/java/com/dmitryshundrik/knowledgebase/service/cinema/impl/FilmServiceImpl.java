package com.dmitryshundrik.knowledgebase.service.cinema.impl;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmArchiveDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.mapper.cinema.FilmMapper;
import com.dmitryshundrik.knowledgebase.repository.cinema.FilmRepository;
import com.dmitryshundrik.knowledgebase.service.cinema.FilmService;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final FilmMapper filmMapper;

    @Override
    public Film getBySlug(String slug) {
        return filmRepository.findBySlug(slug);
    }

    @Override
    public List<FilmArchiveDto> getAllFilmArchiveDto() {
        return filmRepository.findAllFilmArchiveDtoOrderByCreatedDesc();
    }

    @Override
    public List<FilmResponseDto> getTop20BestFilms() {
        return filmRepository.findAllByAllTimeRankNotNullAndOrderByAllTimeRankAsc();
    }

    @Override
    public List<Film> getLatestUpdate() {
        return filmRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public Film createFilm(FilmCreateEditDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        film.setSlug(SlugFormatter.baseFormatter(film.getSlug()));
        return filmRepository.save(film);
    }

    @Override
    public Film updateFilm(String filmSlug, FilmCreateEditDto filmDto) {
        Film film = filmRepository.findBySlug(filmSlug);
        filmMapper.updateFilm(film, filmDto);
        return film;
    }

    @Override
    public void deleteFilmBySlug(String filmSlug) {
        filmRepository.delete(filmRepository.findBySlug(filmSlug));
    }

    @Override
    public void updateFilmImageBySlug(String filmSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Film bySlug = filmRepository.findBySlug(filmSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    @Override
    public void deleteFilmImage(String filmSlug) {
        Film bySlug = filmRepository.findBySlug(filmSlug);
        bySlug.setImage(null);
    }

    @Override
    public FilmCreateEditDto getFilmCreateEditDto(Film film) {
        return filmMapper.toFilmCreateEditDto(film);
    }

    @Override
    public String isSlugExists(String filmSlug) {
        String message = "";
        if (filmRepository.findBySlug(filmSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return filmRepository.getSize();
    }
}
