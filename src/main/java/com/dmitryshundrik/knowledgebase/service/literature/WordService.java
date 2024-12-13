package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.dto.literature.WordDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.WordRepository;
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
public class WordService {

    private final WordRepository wordRepository;

    @Transactional(readOnly = true)
    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Word> getAllSortedByCreatedDesc() {
        return wordRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<Word> getAllByWriterSortedByCreatedDesc(Writer writer) {
        return wordRepository.getAllByWriterOrderByCreatedDesc(writer);
    }

    @Transactional(readOnly = true)
    public List<Word> getAllByWriterSortedByTitle(Writer writer) {
        return wordRepository.getAllByWriterOrderByTitle(writer);
    }

    @Transactional(readOnly = true)
    public Word getById(String wordId) {
        return wordRepository.findById(UUID.fromString(wordId)).orElse(null);
    }

    public Word createWord(WordDTO wordDTO, Writer writer) {
        Word word = new Word();
        word.setWriter(writer);
        word.setTitle(wordDTO.getTitle());
        word.setDescription(wordDTO.getDescription());
        return wordRepository.save(word);
    }

    public Word updateWord(WordDTO wordDTO, String wordId) {
        Word word = getById(wordId);
        word.setTitle(wordDTO.getTitle().trim());
        word.setDescription(wordDTO.getDescription().trim());
        return word;
    }

    public WordDTO getWordDTO(Word word) {
        return WordDTO.builder()
                .id(word.getId().toString())
                .created(InstantFormatter.instantFormatterDMY(word.getCreated()))
                .writerNickname(word.getWriter().getNickName())
                .writerSlug(word.getWriter().getSlug())
                .title(word.getTitle())
                .description(word.getDescription())
                .build();
    }

    public List<WordDTO> getWordDTOList(List<Word> wordList) {
        return wordList.stream()
                .map(this::getWordDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String wordId) {
        wordRepository.deleteById(UUID.fromString(wordId));
    }
}
