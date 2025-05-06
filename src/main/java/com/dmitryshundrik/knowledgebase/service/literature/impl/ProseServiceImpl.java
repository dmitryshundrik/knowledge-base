package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.mapper.literature.ProseMapper;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDto;
import com.dmitryshundrik.knowledgebase.repository.literature.ProseRepository;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class ProseServiceImpl implements ProseService {

    private final ProseRepository proseRepository;

    private final ProseMapper proseMapper;

    @Override
    public Prose getById(String id) {
        return proseRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public Prose getBySlug(String proseSlug) {
        return proseRepository.findBySlug(proseSlug);
    }

    @Override
    public List<Prose> getAll() {
        return proseRepository.findAll();
    }

    @Override
    public List<Prose> getAllOrderByCreated() {
        return proseRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Prose> getAllByWriter(Writer writer) {
        return proseRepository.findAllByWriter(writer);
    }

    @Override
    public List<Prose> getFirst5ByWriterOrderByRating(Writer writer) {
        return proseRepository.findFirst5ByWriterOrderByRatingDesc(writer);
    }

    @Override
    public List<Prose> getLatestUpdate() {
        return proseRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public Prose createProse(Writer writer, ProseCreateEditDto proseDto) {
        Prose prose = proseMapper.toProse(proseDto);
        prose.setWriter(writer);
        prose.setSlug(writer.getSlug() + "-" + SlugFormatter.slugFormatter(prose.getSlug()));
        return proseRepository.save(prose);
    }

    @Override
    public Prose updateProse(Prose prose, ProseCreateEditDto proseDto) {
        proseMapper.updateProse(prose, proseDto);
        return prose;
    }

    @Override
    public void deleteProse(String proseSlug) {
        Prose bySlug = getBySlug(proseSlug);
        proseRepository.delete(bySlug);
    }

    @Override
    public void updateSynopsisSchema(String proseSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Prose bySlug = getBySlug(proseSlug);
            bySlug.setPlayCharactersSchema(new String(bytes));
        }
    }

    @Override
    public void deleteSynopsisSchema(String proseSlug) {
        Prose bySlug = getBySlug(proseSlug);
        bySlug.setPlayCharactersSchema(null);
    }

    @Override
    public ProseViewDto getProseViewDto(Prose prose) {
        return proseMapper.toProseViewDto(new ProseViewDto(), prose);
    }

    @Override
    public List<ProseViewDto> getProseViewDtoList(List<Prose> proseList) {
        return proseList.stream()
                .map(this::getProseViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProseCreateEditDto getProseCreateEditDto(Prose prose) {
        return proseMapper.toProseCreateEditDto(new ProseCreateEditDto(), prose);
    }

    @Override
    public ProseSelectDto getProseSelectDto(Prose prose) {
        return ProseSelectDto.builder().id(prose.getId().toString()).title(prose.getTitle()).build();
    }

    @Override
    public List<ProseSelectDto> getProseSelectDtoList(List<Prose> proseList) {
        return proseList.stream().map(this::getProseSelectDto).collect(Collectors.toList());
    }

    @Override
    public String isSlugExists(String proseSlug) {
        String message = "";
        if (getBySlug(proseSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return proseRepository.getSize();
    }
}
