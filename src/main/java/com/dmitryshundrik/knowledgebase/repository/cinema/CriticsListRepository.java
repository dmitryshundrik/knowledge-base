package com.dmitryshundrik.knowledgebase.repository.cinema;

import com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.entity.cinema.CriticsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface CriticsListRepository extends JpaRepository<CriticsList, UUID> {

    @Query(value = "select count(m) from CriticsList m")
    Long getSize();

    CriticsList findBySlug(String slug);

    List<CriticsList> findFirst20ByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListResponseDto(cticsList.created, cticsList.slug, cticsList.title, " +
            "cticsList.year, cticsList.synopsis) FROM CriticsList cticsList ORDER BY cticsList.created DESC")
    List<CriticsListResponseDto> findAllCriticsListArchiveDtoOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListResponseDto(cticsList.created, cticsList.slug, cticsList.title, " +
            "cticsList.year, cticsList.synopsis) FROM CriticsList cticsList ORDER BY cticsList.year DESC")
    List<CriticsListResponseDto> findAllCriticsListResponseDtoOrderByYearDesc();
}
