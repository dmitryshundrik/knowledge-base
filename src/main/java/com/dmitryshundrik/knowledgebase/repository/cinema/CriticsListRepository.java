package com.dmitryshundrik.knowledgebase.repository.cinema;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.CriticsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CriticsListRepository extends JpaRepository<CriticsList, UUID> {

    Optional<CriticsList> findBySlug(String slug);

    List<CriticsList> findFirst20ByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto(cticsList.created, cticsList.slug, cticsList.title, " +
            "cticsList.year, cticsList.synopsis) " +
            "FROM CriticsList cticsList " +
            "ORDER BY cticsList.created DESC")
    List<CriticsListResponseDto> findAllCriticsListArchiveDtoOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto(cticsList.created, cticsList.slug, cticsList.title, " +
            "cticsList.year, cticsList.synopsis) " +
            "FROM CriticsList cticsList " +
            "ORDER BY cticsList.year DESC")
    List<CriticsListResponseDto> findAllCriticsListResponseDtoOrderByYearDesc();

    @Query(value = "select count(m) from CriticsList m")
    Long getSize();
}
