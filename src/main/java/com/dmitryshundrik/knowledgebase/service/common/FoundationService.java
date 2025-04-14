package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.entity.common.Foundation;
import com.dmitryshundrik.knowledgebase.model.dto.common.FoundationDto;
import com.dmitryshundrik.knowledgebase.repository.common.FoundationRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FoundationService {

    private final FoundationRepository foundationRepository;

    public Foundation getById(String id) {
        return foundationRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Foundation> getAll() {
        return foundationRepository.findAll();
    }

    public List<Foundation> getAllSortedByCreated() {
        return foundationRepository.findAllByOrderByCreatedAsc();
    }

    public Foundation createFoundation(FoundationDto foundationDto) {
        Foundation foundation = new Foundation();
        setFieldsFromDto(foundation, foundationDto);
        return foundationRepository.save(foundation);
    }

    public Foundation updateFoundation(String foundationId, FoundationDto foundationDto) {
        Foundation byId = getById(foundationId);
        setFieldsFromDto(byId, foundationDto);
        return byId;
    }

    public void deleteFoundation(String foundationId) {
        foundationRepository.deleteById(UUID.fromString(foundationId));
    }

    public FoundationDto getFoundationDto(Foundation foundation) {
        return FoundationDto.builder()
                .id(foundation.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(foundation.getCreated()))
                .title(foundation.getTitle())
                .description(foundation.getDescription())
                .link(foundation.getLink())
                .build();
    }

    public List<FoundationDto> getFoundationDTOList(List<Foundation> foundationList) {
        return foundationList.stream()
                .map(this::getFoundationDto)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDto(Foundation foundation, FoundationDto foundationDto) {
        foundation.setTitle(foundationDto.getTitle());
        foundation.setDescription(foundationDto.getDescription());
        foundation.setLink(foundationDto.getLink());
    }
}
