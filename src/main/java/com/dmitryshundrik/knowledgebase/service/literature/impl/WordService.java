package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
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

    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    public List<Word> getAllSortedByCreatedDesc() {
        return wordRepository.findAllByOrderByCreatedDesc();
    }

    public List<Word> getAllByWriterSortedByTitle(Writer writer) {
        return wordRepository.findAllByWriterOrderByTitle(writer);
    }

    public Word getById(String wordId) {
        return wordRepository.findById(UUID.fromString(wordId)).orElse(null);
    }

    public Word createWord(WordDto wordDto, Writer writer) {
        Word word = new Word();
        word.setWriter(writer);
        word.setTitle(wordDto.getTitle());
        word.setDescription(wordDto.getDescription());
        return wordRepository.save(word);
    }

    public Word updateWord(WordDto wordDto, String wordId) {
        Word word = getById(wordId);
        word.setTitle(wordDto.getTitle().trim());
        word.setDescription(wordDto.getDescription().trim());
        return word;
    }

    public WordDto getWordDto(Word word) {
        return WordDto.builder()
                .id(word.getId().toString())
                .created(InstantFormatter.instantFormatterDMY(word.getCreated()))
                .writerNickname(word.getWriter().getNickName())
                .writerSlug(word.getWriter().getSlug())
                .title(word.getTitle())
                .description(word.getDescription())
                .build();
    }

    public List<WordDto> getWordDtoList(List<Word> wordList) {
        return wordList.stream()
                .map(this::getWordDto)
                .collect(Collectors.toList());
    }

    public void deleteById(String wordId) {
        wordRepository.deleteById(UUID.fromString(wordId));
    }
}
