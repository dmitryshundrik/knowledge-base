package com.dmitryshundrik.knowledgebase.mapper.cinema;

import com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListCreateEditDto;
import com.dmitryshundrik.knowledgebase.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.entity.cinema.CriticsList;
import com.dmitryshundrik.knowledgebase.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = PaintingService.class)
public interface CriticsListMapper {

    CriticsList toCriticsList(CriticsListCreateEditDto criticsListCreateEditDto);

    void updateCriticsList(CriticsListCreateEditDto criticsListDto, @MappingTarget CriticsList criticsList);
}
