package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseSelectDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.ProseRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProseService {

    @Autowired
    private ProseRepository proseRepository;


    public List<Prose> getAll() {
        return proseRepository.findAll();
    }

    public Prose getBySlug(String proseSlug) {
        return proseRepository.getBySlug(proseSlug);
    }

    public Prose getById(String id) {
        return proseRepository.getById(UUID.fromString(id));
    }

    public List<Prose> getAllByWriter(Writer writer) {
        return proseRepository.getAllByWriter(writer);
    }

    public List<ProseViewDTO> getAllProseSortedByCreatedDesc() {
        return getProseViewDTOList(proseRepository.getAllByOrderByCreatedDesc());
    }

    public Prose createProse(Writer writer, ProseCreateEditDTO proseDTO) {
        Prose prose = new Prose();
        prose.setCreated(Instant.now());
        prose.setWriter(writer);
        setFieldsFromDTO(prose, proseDTO);
        prose.setSlug(SlugFormatter.slugFormatter(prose.getSlug()));
        return proseRepository.save(prose);
    }

    public ProseViewDTO updateProse(Prose prose, ProseCreateEditDTO proseDTO) {
        setFieldsFromDTO(prose, proseDTO);
        return getProseViewDTO(prose);
    }

    public void deleteProse(String proseSlug) {
        Prose bySlug = getBySlug(proseSlug);
        proseRepository.delete(bySlug);
    }

    public ProseCreateEditDTO getProseCreateEditDTO(Prose prose) {
        return ProseCreateEditDTO.builder()
                .slug(prose.getSlug())
                .title(prose.getTitle())
                .writerNickname(prose.getWriter().getNickName())
                .writerSlug(prose.getWriter().getSlug())
                .year(prose.getYear())
                .rating(prose.getRating())
                .description(prose.getDescription())
                .build();
    }

    public ProseViewDTO getProseViewDTO(Prose prose) {
        return ProseViewDTO.builder()
                .created(InstantFormatter.instantFormatterYMD(prose.getCreated()))
                .slug(prose.getSlug())
                .title(prose.getTitle())
                .writerNickname(prose.getWriter().getNickName())
                .writerSlug(prose.getWriter().getSlug())
                .year(prose.getYear())
                .rating(prose.getRating())
                .description(prose.getDescription())
                .build();
    }

    public List<ProseViewDTO> getProseViewDTOList(List<Prose> proseList) {
        return proseList.stream()
                .map(this::getProseViewDTO)
                .collect(Collectors.toList());
    }

    public ProseSelectDTO getProseSelectDTO(Prose prose) {
        return ProseSelectDTO.builder()
                .id(prose.getId().toString())
                .title(prose.getTitle())
                .build();
    }

    public List<ProseSelectDTO> getProseSelectDTOList(List<Prose> proseList) {
        return proseList.stream()
                .map(this::getProseSelectDTO)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDTO(Prose prose, ProseCreateEditDTO proseDTO) {
        prose.setSlug(proseDTO.getSlug().trim());
        prose.setTitle(proseDTO.getTitle().trim());
        prose.setYear(proseDTO.getYear());
        prose.setRating(proseDTO.getRating());
        prose.setDescription(proseDTO.getDescription().trim());
    }

    public String proseSlugIsExist(String proseSlug) {
        String message = "";
        if (getBySlug(proseSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }
}