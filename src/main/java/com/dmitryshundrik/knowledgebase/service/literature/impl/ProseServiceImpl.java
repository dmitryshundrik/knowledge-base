package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
import com.dmitryshundrik.knowledgebase.mapper.literature.ProseMapper;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDto;
import com.dmitryshundrik.knowledgebase.repository.literature.ProseRepository;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.PROSE_NOT_FOUND_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.INVALID_UUID_FORMAT;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.baseFormatter;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.formatProseSlug;

@Service
@Transactional
@RequiredArgsConstructor
public class ProseServiceImpl implements ProseService {

    private final ProseRepository proseRepository;

    private final ProseMapper proseMapper;

    @Override
    public Prose getById(String proseId) {
        try {
            UUID uuid = UUID.fromString(proseId);
            return proseRepository.findById(uuid)
                    .orElseThrow(() -> new NotFoundException(PROSE_NOT_FOUND_MESSAGE.formatted(proseId)));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_UUID_FORMAT.formatted(proseId), e);
        }
    }

    @Override
    public Prose getBySlug(String proseSlug) {
        return proseRepository.findBySlug(proseSlug)
                .orElseThrow(() -> new NotFoundException(PROSE_NOT_FOUND_MESSAGE.formatted(proseSlug)));
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
        proseRepository.save(prose);
        prose.setSlug(formatProseSlug(prose));
        return prose;
    }

    @Override
    public Prose updateProse(Prose prose, ProseCreateEditDto proseDto) {
        proseMapper.updateProse(prose, proseDto);
        prose.setSlug(baseFormatter(prose.getSlug()));
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
        if (proseRepository.findBySlug(proseSlug).isPresent()) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return proseRepository.getSize();
    }
}
