package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;

import java.util.List;

public interface WordService {

    Word getById(String wordId);

    List<Word> getAll();
    List<Word> getAllOrderByCreatedDesc();
    List<Word> getAllByWriterOrderByTitle(Writer writer);

    Word createWord(WordDto wordDto, Writer writer);
    Word updateWord(WordDto wordDto, String wordId);
    void deleteWord(String wordId);

    WordDto getWordDto(Word word);
    List<WordDto> getWordDtoList(List<Word> wordList);
}
