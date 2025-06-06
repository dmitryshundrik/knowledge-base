package com.dmitryshundrik.knowledgebase.mapper.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.Film;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FilmMapper {

    Film toFilm(FilmCreateEditDto filmDto);

    FilmCreateEditDto toFilmCreateEditDto(Film film);

    @Mapping(target = "image", ignore = true)
    void updateFilm(@MappingTarget Film film, FilmCreateEditDto filmDto);
}
