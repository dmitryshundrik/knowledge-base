package com.dmitryshundrik.knowledgebase.service.cinema.impl;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
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

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.CRITICS_LIST_NOT_FOUND_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class CriticsListServiceImpl implements CriticsListService {

    private final CriticsListRepository criticsListRepository;

    private final CriticsListMapper criticsListMapper;

    @Override
    public CriticsList getBySlug(String listSlug) {
        return criticsListRepository.findBySlug(listSlug)
                .orElseThrow(() -> new NotFoundException(CRITICS_LIST_NOT_FOUND_MESSAGE.formatted(listSlug)));
    }

    @Override
    public List<CriticsListResponseDto> getAllCriticsList() {
        return criticsListRepository.findAllCriticsListResponseDtoOrderByYearDesc();
    }

    @Override
    public List<CriticsListResponseDto> getAllCriticsListArchiveDto() {
        return criticsListRepository.findAllCriticsListArchiveDtoOrderByCreatedDesc();
    }

    @Override
    public List<CriticsList> getLatestUpdate() {
        return criticsListRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public CriticsList createCriticsList(CriticsListCreateEditDto criticsListDto) {
        CriticsList criticsList = criticsListMapper.toCriticsList(criticsListDto);
        criticsList.setSlug(SlugFormatter.baseFormatter(criticsList.getSlug()));
        return criticsListRepository.save(criticsList);
    }

    @Override
    public CriticsList updateCriticsList(String criticsListSlug, CriticsListCreateEditDto criticsListDto) {
        CriticsList criticsList = getBySlug(criticsListSlug);
        criticsListMapper.updateCriticsList(criticsList, criticsListDto);
        return criticsList;
    }

    @Override
    public void deleteCriticsList(String criticListSlug) {
        criticsListRepository.delete(getBySlug(criticListSlug));
    }

    @Override
    public CriticsListCreateEditDto getCriticsListCreateEditDto(CriticsList criticsList) {
        return criticsListMapper.toCriticsListCreateEditDto(criticsList);
    }

    @Override
    public String isSlugExists(String criticsListSlug) {
        String message = "";
        if (criticsListRepository.findBySlug(criticsListSlug).isPresent()) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return criticsListRepository.getSize();
    }
}
