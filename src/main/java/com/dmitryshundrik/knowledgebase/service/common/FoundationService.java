package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.entity.common.Foundation;
import com.dmitryshundrik.knowledgebase.dto.common.FoundationDTO;
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

    public Foundation createFoundation(FoundationDTO foundationDTO) {
        Foundation foundation = new Foundation();
        setFieldsFromDTO(foundation, foundationDTO);
        return foundationRepository.save(foundation);
    }

    public Foundation updateFoundation(String foundationId, FoundationDTO foundationDTO) {
        Foundation byId = getById(foundationId);
        setFieldsFromDTO(byId, foundationDTO);
        return byId;
    }

    public void deleteFoundation(String foundationId) {
        foundationRepository.deleteById(UUID.fromString(foundationId));
    }

    public FoundationDTO getFoundationDTO(Foundation foundation) {
        return FoundationDTO.builder()
                .id(foundation.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(foundation.getCreated()))
                .title(foundation.getTitle())
                .description(foundation.getDescription())
                .link(foundation.getLink())
                .build();
    }

    public List<FoundationDTO> getFoundationDTOList(List<Foundation> foundationList) {
        return foundationList.stream()
                .map(this::getFoundationDTO)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDTO(Foundation foundation, FoundationDTO foundationDTO) {
        foundation.setTitle(foundationDTO.getTitle());
        foundation.setDescription(foundationDTO.getDescription());
        foundation.setLink(foundationDTO.getLink());
    }
}
