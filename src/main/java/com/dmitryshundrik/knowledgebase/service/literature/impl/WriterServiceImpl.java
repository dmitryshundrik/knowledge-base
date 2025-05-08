package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.mapper.literature.WriterMapper;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterArchiveListDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterEntityUpdateInfoDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDto;
import com.dmitryshundrik.knowledgebase.repository.literature.WriterRepository;
import com.dmitryshundrik.knowledgebase.service.core.PersonEventService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.baseFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class WriterServiceImpl implements WriterService {

    private final WriterRepository writerRepository;

    private final ProseService proseService;

    private final QuoteService quoteService;

    private final WordService wordService;

    private final PersonEventService personEventService;

    private final WriterMapper writerMapper;

    @Override
    public Writer getBySlug(String writerSlug) {
        return writerRepository.findBySlug(writerSlug);
    }

    @Override
    public List<WriterSimpleDto> getAllOrderByBorn() {
        return writerRepository.findAllOrderByBornAsc();
    }

    @Override
    public List<WriterArchiveListDto> getAllOrderByCreated() {
        return writerRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<WriterEntityUpdateInfoDto> getLatestUpdate() {
        return writerRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public void deleteWriter(String writerSlug) {
        writerRepository.deleteBySlug(writerSlug);
    }

    @Override
    public Writer createWriter(WriterCreateEditDto writerDto) {
        Writer writer = writerMapper.toWriter(writerDto);
        writerRepository.save(writer);
        writer.setSlug(baseFormatter(writer.getSlug()));
        return writer;
    }

    @Override
    public Writer updateWriter(String writerSlug, WriterCreateEditDto writerDto) {
        Writer writer = getBySlug(writerSlug);
        writerMapper.updateWriter(writer, writerDto);
        writer.setSlug(baseFormatter(writer.getSlug()));
        return writer;
    }

    @Override
    public void updateWriterImage(String writerSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Writer bySlug = getBySlug(writerSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    @Override
    public void deleteWriterImage(String writerSlug) {
        Writer bySlug = getBySlug(writerSlug);
        bySlug.setImage(null);
    }

    public WriterViewDto getWriterViewDto(Writer writer) {
        WriterViewDto writerDto = writerMapper.toWriterViewDto(new WriterViewDto(), writer);
        writerDto.setEvents(personEventService.getPersonEventDtoList(writer.getEvents()));
        writerDto.setProseList(proseService.getProseViewDtoList(writer.getProseList().stream()
                .sorted(Comparator.comparing(Prose::getYear, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList()));
        writerDto.setQuoteList(quoteService.getQuoteViewDtoList(writer.getQuoteList().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(10)
                .toList()));
        writerDto.setWordList(wordService.getWordDtoList(writer.getWordList().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(20)
                .toList()));
        return writerDto;
    }

    @Override
    public WriterCreateEditDto getWriterCreateEditDto(Writer writer) {
        WriterCreateEditDto writerDto = writerMapper
                .toWriterCreateEditDto(new WriterCreateEditDto(), writer);
        writerDto.setEvents(personEventService.getPersonEventDtoList(writer.getEvents()));
        writerDto.setProseList(proseService.getProseViewDtoList(writer.getProseList().stream()
                .sorted(Comparator.comparing(Prose::getYear, Comparator.nullsFirst(Comparator.naturalOrder())))
                .toList()));
        writerDto.setQuoteList(quoteService.getQuoteViewDtoList(writer.getQuoteList().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .toList()));
        writerDto.setWordList(wordService.getWordDtoList(writer.getWordList().stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .toList()));
        return writerDto;
    }

    @Override
    public Set<Writer> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Writer> writerBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            writerBirthList.addAll(writerRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i), null));
        }
        return writerBirthList;
    }

    @Override
    public Set<Writer> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Writer> writerDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            writerDeathList.addAll(writerRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i), null));
        }
        return writerDeathList;
    }

    @Override
    public Set<Writer> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Writer> writerBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            writerBirthList.addAll(writerRepository
                    .findAllWithCurrentBirth(LocalDate.now().plusDays(i), true));
        }
        return writerBirthList;
    }

    @Override
    public Set<Writer> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Writer> writerDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            writerDeathList.addAll(writerRepository
                    .findAllWithCurrentBirth(LocalDate.now().plusDays(i), true));
        }
        return writerDeathList;
    }

    @Override
    public String isSlugExists(String writerSlug) {
        String message = "";
        if (getBySlug(writerSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return writerRepository.getSize();
    }
}
