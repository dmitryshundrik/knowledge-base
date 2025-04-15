package com.dmitryshundrik.knowledgebase.service.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.CriticsList;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

/**
 * Service interface for managing {@link CriticsList} entities.
 * Provides methods for retrieving, creating, updating, and deleting critics lists, as well as handling
 * list-related data, and DTO conversions.
 */
public interface CriticsListService extends BaseService {

    CriticsList getBySlug(String slug);

    List<CriticsListResponseDto> getAllCriticsList();
    List<CriticsListResponseDto> getAllCriticsListArchiveDto();
    List<CriticsList> getLatestUpdate();

    CriticsList createCriticsList(CriticsListCreateEditDto criticsListDto);
    CriticsList updateCriticsList(String filmSlug, CriticsListCreateEditDto criticsListDto);
    void deleteCriticsListBySlug(String criticListSlug);

    CriticsListCreateEditDto getCriticsListCreateEditDto(CriticsList criticsList);
}
