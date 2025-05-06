package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.literature.impl.WordServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.WORD;
import static com.dmitryshundrik.knowledgebase.util.Constants.WORD_LIST;

@Controller
@RequiredArgsConstructor
public class WordManagementController {

    private final WordServiceImpl wordService;

    private final WriterService writerService;

    @GetMapping("/management/word/all")
    public String getAllWords(Model model) {
        List<Word> wordList = wordService.getAllOrderByCreatedDesc();
        List<WordDto> wordDtoList = wordService.getWordDtoList(wordList);
        model.addAttribute(WORD_LIST, wordDtoList);
        return "management/literature/word-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/word/create")
    public String getWordCreate(@PathVariable String writerSlug, Model model) {
        WordDto wordDto = new WordDto();
        wordDto.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        wordDto.setWriterSlug(writerSlug);
        model.addAttribute(WORD, wordDto);
        return "management/literature/word-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/word/create")
    public String postWordCreate(@PathVariable String writerSlug,
                                 @ModelAttribute(WORD) WordDto wordDto) {
        Writer writer = writerService.getBySlug(writerSlug);
        Word word = wordService.createWord(wordDto, writer);
        return "redirect:/management/writer/edit/" + writerSlug + "/word/edit/" + word.getId();
    }

    @GetMapping("/management/writer/edit/{writerSlug}/word/edit/{wordId}")
    public String getWordEdit(@PathVariable String writerSlug, @PathVariable String wordId, Model model) {
        Word word = wordService.getById(wordId);
        WordDto wordDto = wordService.getWordDto(word);
        model.addAttribute(WORD, wordDto);
        return "management/literature/word-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/word/edit/{wordId}")
    public String putWordEdit(@PathVariable String writerSlug, @PathVariable String wordId,
                              @ModelAttribute(WORD) WordDto wordDto) {
        Word word = wordService.updateWord(wordDto, wordId);
        return "redirect:/management/writer/edit/" + writerSlug + "/word/edit/" + word.getId();
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/word/delete/{wordId}")
    public String deleteWordById(@PathVariable String writerSlug, @PathVariable String wordId) {
        wordService.deleteWord(wordId);
        return "redirect:/management/writer/edit/" + writerSlug;
    }
}
