package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.mapper.literature.WordMapper;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
import com.dmitryshundrik.knowledgebase.repository.literature.WordRepository;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    private final WordMapper wordMapper;

    @Override
    public Word getById(String wordId) {
        return wordRepository.findById(UUID.fromString(wordId)).orElse(null);
    }

    @Override
    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    @Override
    public List<Word> getAllOrderByCreatedDesc() {
        return wordRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Word> getAllByWriterOrderByTitle(Writer writer) {
        return wordRepository.findAllByWriterOrderByTitle(writer);
    }

    @Override
    public Word createWord(WordDto wordDto, Writer writer) {
        Word word = new Word();
        word.setWriter(writer);
        word.setTitle(wordDto.getTitle());
        word.setDescription(wordDto.getDescription());
        return wordRepository.save(word);
    }

    @Override
    public Word updateWord(WordDto wordDto, String wordId) {
        Word word = getById(wordId);
        word.setTitle(wordDto.getTitle().trim());
        word.setDescription(wordDto.getDescription().trim());
        return word;
    }

    @Override
    public void deleteWord(String wordId) {
        wordRepository.deleteById(UUID.fromString(wordId));
    }

    @Override
    public WordDto getWordDto(Word word) {
        return wordMapper.toWordDto(new WordDto(), word);
    }

    @Override
    public List<WordDto> getWordDtoList(List<Word> wordList) {
        return wordList.stream()
                .map(this::getWordDto)
                .collect(Collectors.toList());
    }
}
