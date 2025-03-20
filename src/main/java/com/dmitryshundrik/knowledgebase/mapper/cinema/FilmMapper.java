package com.dmitryshundrik.knowledgebase.mapper.cinema;

import com.dmitryshundrik.knowledgebase.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = PaintingService.class)
public interface FilmMapper {

    Film toFilm(FilmCreateEditDto filmCreateEditDto);

    @Mapping(target = "image", ignore = true)
    void updateFilm(FilmCreateEditDto filmDto, @MappingTarget Film film);
}
