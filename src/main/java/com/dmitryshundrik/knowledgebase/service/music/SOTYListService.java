package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.dto.SOTYListCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.SOTYListViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.SOTYListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SOTYListService {

    @Autowired
    private SOTYListRepository sotyListRepository;

    public List<SOTYList> getAllSOTYLists() {
        return sotyListRepository.findAll();
    }

    public SOTYList getSOTYListBySlug(String slug) {
        return sotyListRepository.getSOTYListBySlug(slug);
    }

    public void createSOTYListbySOTYListDTO (SOTYListCreateEditDTO sotyListDTO) {
        SOTYList sotyList = new SOTYList();
        sotyList.setCreated(Instant.now());
        setFieldsFromDTO(sotyList, sotyListDTO);
        sotyListRepository.save(sotyList);
    }

    public void updateExistingSOTYList(SOTYListCreateEditDTO sotyListDTO, String slug) {
        SOTYList sotyListBySlug = getSOTYListBySlug(slug);
        setFieldsFromDTO(sotyListBySlug, sotyListDTO);
    }

    public void deleteSOTYListBySlug(String slug) {
        sotyListRepository.delete(getSOTYListBySlug(slug));
    }

    public SOTYListViewDTO getSOTYListViewDTO(SOTYList sotyList) {
        return SOTYListViewDTO.builder()
                .created(sotyList.getCreated())
                .slug(sotyList.getSlug())
                .title(sotyList.getTitle())
                .year(sotyList.getYear())
                .description(sotyList.getDescription())
                .spotifyLink(sotyList.getSpotifyLink())
                .build();
    }

    public List<SOTYListViewDTO> getSOTYListViewDTOList(List<SOTYList> sotyListList) {
        return sotyListList.stream().map(sotyList -> getSOTYListViewDTO(sotyList)).collect(Collectors.toList());
    }

    public SOTYListCreateEditDTO getSOTYListCreateEditDTO(SOTYList sotyList) {
        return SOTYListCreateEditDTO.builder()
                .slug(sotyList.getSlug())
                .title(sotyList.getTitle())
                .year(sotyList.getYear())
                .description(sotyList.getDescription())
                .spotifyLink(sotyList.getSpotifyLink())
                .build();
    }

    public void setFieldsFromDTO(SOTYList sotyList, SOTYListCreateEditDTO sotyListDTO) {
        sotyList.setSlug(sotyListDTO.getSlug());
        sotyList.setTitle(sotyListDTO.getTitle());
        sotyList.setYear(sotyListDTO.getYear());
        sotyList.setDescription(sotyListDTO.getDescription());
        sotyList.setSpotifyLink(sotyListDTO.getSpotifyLink());
    }
}
