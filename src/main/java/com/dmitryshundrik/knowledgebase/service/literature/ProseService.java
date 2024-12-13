package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.dto.literature.ProseCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.literature.ProseSelectDTO;
import com.dmitryshundrik.knowledgebase.dto.literature.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.ProseRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class ProseService {

    private final ProseRepository proseRepository;

    @Transactional(readOnly = true)
    public List<Prose> getAll() {
        return proseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Prose> getAllSortedByYear() {
        List<Prose> all = getAll();
        return all.stream()
                .sorted(Comparator.comparing(Prose::getYear))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Prose getBySlug(String proseSlug) {
        return proseRepository.getBySlug(proseSlug);
    }

    @Transactional(readOnly = true)
    public Prose getById(String id) {
        return proseRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Prose> getAllByWriter(Writer writer) {
        return proseRepository.getAllByWriter(writer);
    }

    @Transactional(readOnly = true)
    public List<Prose> getAllByWriterSortedByRating(Writer writer) {
        List<Prose> allByWriter = getAllByWriter(writer).stream()
                .filter(prose -> prose.getRating() != null)
                .toList();
        return allByWriter.stream()
                .sorted((o1, o2) -> o2.getRating().compareTo(o1.getRating()))
                .limit(5)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Prose> getAllByWriterSortedByYear(Writer writer) {
        return writer.getProseList().stream()
                .sorted(Comparator.comparing(Prose::getYear))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Prose> getAllProseSortedByCreatedDesc() {
        return proseRepository.getAllByOrderByCreatedDesc();
    }

    public Prose createProse(Writer writer, ProseCreateEditDTO proseDTO) {
        Prose prose = new Prose();
        prose.setWriter(writer);
        setFieldsFromDTO(prose, proseDTO);
        prose.setSlug(writer.getSlug() + "-" + SlugFormatter.slugFormatter(prose.getSlug()));
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

    public void updateSynopsisSchemaBySlug(String proseSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Prose bySlug = getBySlug(proseSlug);
            bySlug.setPlayCharactersSchema(new String(bytes));
        }
    }

    public void deleteSynopsisSchema(String proseSlug) {
        Prose bySlug = getBySlug(proseSlug);
        bySlug.setPlayCharactersSchema(null);
    }

    public ProseCreateEditDTO getProseCreateEditDTO(Prose prose) {
        return ProseCreateEditDTO.builder()
                .slug(prose.getSlug())
                .title(prose.getTitle())
                .writerNickname(prose.getWriter().getNickName())
                .writerSlug(prose.getWriter().getSlug())
                .year(prose.getYear())
                .rating(prose.getRating())
                .playCharactersSchema(prose.getPlayCharactersSchema())
                .synopsis(prose.getSynopsis())
                .description(prose.getDescription())
                .build();
    }

    public ProseViewDTO getProseViewDTO(Prose prose) {
        return ProseViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(prose.getCreated()))
                .slug(prose.getSlug())
                .title(prose.getTitle())
                .writerNickname(prose.getWriter().getNickName())
                .writerSlug(prose.getWriter().getSlug())
                .year(prose.getYear())
                .rating(prose.getRating())
                .playCharactersSchema(prose.getPlayCharactersSchema())
                .synopsis(prose.getSynopsis())
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
        prose.setSynopsis(proseDTO.getSynopsis());
        prose.setDescription(proseDTO.getDescription().trim());
    }

    public String proseSlugIsExist(String proseSlug) {
        String message = "";
        if (getBySlug(proseSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<Prose> getLatestUpdate() {
        return proseRepository.findFirst20ByOrderByCreatedDesc();
    }
}
