package com.dmitryshundrik.knowledgebase.service.cinema.impl;

import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.cinema.CriticsList;
import com.dmitryshundrik.knowledgebase.mapper.cinema.CriticsListMapper;
import com.dmitryshundrik.knowledgebase.repository.cinema.CriticsListRepository;
import com.dmitryshundrik.knowledgebase.service.cinema.CriticsListService;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class CriticsListServiceImpl implements CriticsListService {

    private final CriticsListRepository criticsListRepository;

    private final CriticsListMapper criticsListMapper;

    public CriticsList getBySlug(String slug) {
        return criticsListRepository.findBySlug(slug);
    }

    public List<CriticsListResponseDto> getAllCriticsList() {
        return criticsListRepository.findAllCriticsListResponseDtoOrderByYearDesc();
    }

    public List<CriticsListResponseDto> getAllCriticsListArchiveDto() {
        return criticsListRepository.findAllCriticsListArchiveDtoOrderByCreatedDesc();
    }

    public List<CriticsList> getLatestUpdate() {
        return criticsListRepository.findFirst20ByOrderByCreatedDesc();
    }

    public CriticsList createCriticsList(CriticsListCreateEditDto criticsListDto) {
        CriticsList criticsList = criticsListMapper.toCriticsList(criticsListDto);
        criticsList.setSlug(SlugFormatter.slugFormatter(criticsList.getSlug()));
        return criticsListRepository.save(criticsList);
    }

    public CriticsList updateCriticsList(String filmSlug, CriticsListCreateEditDto criticsListDto) {
        CriticsList criticsList = criticsListRepository.findBySlug(filmSlug);
        criticsListMapper.updateCriticsList(criticsListDto, criticsList);
        return criticsList;
    }

    public void deleteCriticsListBySlug(String criticListSlug) {
        criticsListRepository.delete(criticsListRepository.findBySlug(criticListSlug));
    }

    public CriticsListCreateEditDto getCriticsListCreateEditDto(CriticsList criticsList) {
        return criticsListMapper.toCriticsListCreateEditDto(criticsList);
    }

    public String isSlugExist(String criticsListSlug) {
        String message = "";
        if (criticsListRepository.findBySlug(criticsListSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public Long getRepositorySize() {
        return criticsListRepository.getSize();
    }
}
