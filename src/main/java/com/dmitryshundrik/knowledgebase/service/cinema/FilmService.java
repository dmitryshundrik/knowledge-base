package com.dmitryshundrik.knowledgebase.service.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmArchiveDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.mapper.cinema.FilmMapper;
import com.dmitryshundrik.knowledgebase.repository.cinema.FilmRepository;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    private final FilmMapper filmMapper;

    public Long getFilmRepositorySize() {
        return filmRepository.getSize();
    }

    public Film getBySlug(String slug) {
        return filmRepository.findBySlug(slug);
    }

    public List<FilmArchiveDto> getAllFilmArchiveDto() {
        return filmRepository.findAllFilmArchiveDtoOrderByCreatedDesc();
    }

    public List<FilmResponseDto> getTop20BestFilms() {
        return filmRepository.findAllByAllTimeRankNotNullAndOrderByAllTimeRankAsc();
    }

    public Film createFilm(FilmCreateEditDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        film.setSlug(SlugFormatter.slugFormatter(film.getSlug()));
        return filmRepository.save(film);
    }

    public Film updateFilm(String filmSlug, FilmCreateEditDto filmDto) {
        Film film = filmRepository.findBySlug(filmSlug);
        filmMapper.updateFilm(filmDto, film);
        return film;
    }

    public void deleteFilmBySlug(String filmSlug) {
        filmRepository.delete(filmRepository.findBySlug(filmSlug));
    }

    public FilmCreateEditDto getFilmCreateEditDto(Film film) {
        return FilmCreateEditDto.builder()
                .slug(film.getSlug())
                .title(film.getTitle())
                .director(film.getDirector())
                .starring(film.getStarring())
                .year(film.getYear())
                .rating(film.getRating())
                .yearRank(film.getYearRank())
                .allTimeRank(film.getAllTimeRank())
                .synopsis(film.getSynopsis())
                .image(film.getImage())
                .build();
    }

    public void updateFilmImageBySlug(String filmSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Film bySlug = filmRepository.findBySlug(filmSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    public void deleteFilmImage(String filmSlug) {
        Film bySlug = filmRepository.findBySlug(filmSlug);
        bySlug.setImage(null);
    }

    public String filmSlugIsExist(String filmSlug) {
        String message = "";
        if (filmRepository.findBySlug(filmSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<Film> getLatestUpdate() {
        return filmRepository.findFirst20ByOrderByCreatedDesc();
    }
}
