package com.dmitryshundrik.knowledgebase.mapper.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.CriticsList;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CriticsListMapper {

    CriticsList toCriticsList(CriticsListCreateEditDto criticsListCreateEditDto);

    void updateCriticsList(CriticsListCreateEditDto criticsListDto, @MappingTarget CriticsList criticsList);

    CriticsListCreateEditDto toCriticsListCreateEditDto(CriticsList criticsList);
}
