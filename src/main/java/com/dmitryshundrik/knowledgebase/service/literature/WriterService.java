package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterViewDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.WriterRepository;
import com.dmitryshundrik.knowledgebase.service.common.PersonEventService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class WriterService {

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private ProseService proseService;

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private WordService wordService;

    @Autowired
    private PersonEventService personEventService;


    public List<Writer> getAll() {
        return writerRepository.findAll();
    }

    public List<Writer> getAllSortedByBorn() {
        return writerRepository.findAllByOrderByBorn();
    }

    public List<Writer> getAllSortedByCreatedDesc() {
        return writerRepository.getAllByOrderByCreatedDesc();
    }

    public Writer getBySlug(String writerSlug) {
        return writerRepository.findBySlug(writerSlug);
    }

    public Writer createWriter(WriterCreateEditDTO writerDTO) {
        Writer writer = new Writer();
        writer.setCreated(Instant.now());
        setFieldsFromDTO(writer, writerDTO);
        writer.setSlug(SlugFormatter.slugFormatter(writer.getSlug()));
        return writerRepository.save(writer);
    }

    public WriterViewDTO updateWriter(String writerSlug, WriterCreateEditDTO writerDTO) {
        Writer bySlug = getBySlug(writerSlug);
        setFieldsFromDTO(bySlug, writerDTO);
        return getWriterViewDTO(bySlug);
    }

    public void updateWriterImageBySlug(String writerSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Writer bySlug = getBySlug(writerSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    public void deleteWriterImage(String writerSlug) {
        Writer bySlug = getBySlug(writerSlug);
        bySlug.setImage(null);
    }

    public void deleteWriterBySlug(String writerSlug) {
        writerRepository.deleteBySlug(writerSlug);
    }


    public WriterViewDTO getWriterViewDTO(Writer writer) {
        return WriterViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(writer.getCreated()))
                .slug(writer.getSlug())
                .firstName(writer.getFirstName())
                .lastName(writer.getLastName())
                .nickName(writer.getNickName())
                .gender(writer.getGender())
                .image(writer.getImage())
                .born(writer.getBorn())
                .died(writer.getDied())
                .birthDate(writer.getBirthDate())
                .deathDate(writer.getDeathDate())
                .birthplace(writer.getBirthplace())
                .occupation(writer.getOccupation())
                .events(personEventService.getPersonEventDTOList(writer.getEvents()))
                .proseList(proseService.getProseViewDTOList(proseService.getAllByWriterSortedByYear(writer)))
                .quoteList(quoteService.getQuoteViewDTOList(quoteService.getAllByWriterSortedByCreatedDesc(writer).stream()
                        .limit(10).collect(Collectors.toList())))
                .wordList(wordService.getWordDTOList(wordService.getAllByWriterSortedByCreatedDesc(writer).stream()
                        .limit(20).collect(Collectors.toList())))
                .build();
    }

    public List<WriterViewDTO> getWriterViewDTOList(List<Writer> writerList) {
        return writerList.stream().map(this::getWriterViewDTO)
                .collect(Collectors.toList());
    }

    public WriterCreateEditDTO getWriterCreateEditDTO(Writer writer) {
        return WriterCreateEditDTO.builder()
                .slug(writer.getSlug())
                .firstName(writer.getFirstName())
                .lastName(writer.getLastName())
                .nickName(writer.getNickName())
                .gender(writer.getGender())
                .image(writer.getImage())
                .born(writer.getBorn())
                .died(writer.getDied())
                .birthDate(writer.getBirthDate())
                .deathDate(writer.getDeathDate())
                .birthplace(writer.getBirthplace())
                .occupation(writer.getOccupation())
                .events(personEventService.getPersonEventDTOList(writer.getEvents()))
                .proseList(proseService.getProseViewDTOList(proseService.getAllByWriterSortedByYear(writer)))
                .quoteList(quoteService.getQuoteViewDTOList(quoteService.getAllByWriterSortedByCreatedDesc(writer)))
                .wordList(wordService.getWordDTOList(wordService.getAllByWriterSortedByCreatedDesc(writer)))
                .build();
    }

    private void setFieldsFromDTO(Writer writer, WriterCreateEditDTO writerDTO) {
        writer.setSlug(writerDTO.getSlug().trim());
        writer.setNickName(writerDTO.getNickName().trim());
        writer.setFirstName(writerDTO.getFirstName().trim());
        writer.setLastName(writerDTO.getLastName().trim());
        writer.setGender(writerDTO.getGender());
        writer.setBorn(writerDTO.getBorn());
        writer.setDied(writerDTO.getDied());
        writer.setBirthDate(writerDTO.getBirthDate());
        writer.setDeathDate(writerDTO.getDeathDate());
        writer.setBirthplace(writerDTO.getBirthplace().trim());
        writer.setOccupation(writerDTO.getOccupation().trim());
    }

    public String writerSlugIsExist(String writerSlug) {
        String message = "";
        if (getBySlug(writerSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

    public List<Writer> getLatestUpdate() {
        return writerRepository.findFirst20ByOrderByCreatedDesc();
    }

    public Set<Writer> getAllWithCurrentBirth() {
        Set<Writer> writerBirthList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            writerBirthList.addAll(writerRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i)));
        }
        return writerBirthList;
    }

    public Set<Writer> getAllWithCurrentDeath() {
        Set<Writer> writerDeathList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            writerDeathList.addAll(writerRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i)));
        }
        return writerDeathList;
    }

}
