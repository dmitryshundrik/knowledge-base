package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.dto.literature.WordDTO;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
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

    private final WordService wordService;

    private final WriterService writerService;

    @GetMapping("/management/word/all")
    public String getAllWords(Model model) {
        List<Word> allSortedByCreatedDesc = wordService.getAllSortedByCreatedDesc();
        List<WordDTO> wordDTOList = wordService.getWordDTOList(allSortedByCreatedDesc);
        model.addAttribute(WORD_LIST, wordDTOList);
        return "management/literature/word-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/word/create")
    public String getWordCreate(@PathVariable String writerSlug, Model model) {
        WordDTO wordDTO = new WordDTO();
        wordDTO.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        wordDTO.setWriterSlug(writerSlug);
        model.addAttribute(WORD, wordDTO);
        return "management/literature/word-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/word/create")
    public String postWordCreate(@PathVariable String writerSlug,
                                 @ModelAttribute(WORD) WordDTO wordDTO) {
        Writer writerServiceBySlug = writerService.getBySlug(writerSlug);
        Word savedWord = wordService.createWord(wordDTO, writerServiceBySlug);
        return "redirect:/management/writer/edit/" + writerSlug + "/word/edit/" + savedWord.getId();
    }

    @GetMapping("/management/writer/edit/{writerSlug}/word/edit/{wordId}")
    public String getWordEdit(@PathVariable String writerSlug, @PathVariable String wordId, Model model) {
        Word byId = wordService.getById(wordId);
        WordDTO wordDTO = wordService.getWordDTO(byId);
        model.addAttribute(WORD, wordDTO);
        return "management/literature/word-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/word/edit/{wordId}")
    public String putWordEdit(@PathVariable String writerSlug, @PathVariable String wordId,
                              @ModelAttribute(WORD) WordDTO wordDTO) {
        Word updatedWord = wordService.updateWord(wordDTO, wordId);
        return "redirect:/management/writer/edit/" + writerSlug + "/word/edit/" + updatedWord.getId();
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/word/delete/{wordId}")
    public String deleteWordById(@PathVariable String writerSlug, @PathVariable String wordId) {
        wordService.deleteById(wordId);
        return "redirect:/management/writer/edit/" + writerSlug;
    }
}
