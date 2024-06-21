package com.dmitryshundrik.knowledgebase.controller.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Word;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WordDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterViewDTO;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/literature")
public class LiteraturePageController {

    private final WriterService writerService;

    private final ProseService proseService;

    private final QuoteService quoteService;

    private final WordService wordService;

    public LiteraturePageController(WriterService writerService, ProseService proseService, QuoteService quoteService,
                                    WordService wordService) {
        this.writerService = writerService;
        this.proseService = proseService;
        this.quoteService = quoteService;
        this.wordService = wordService;
    }

    @GetMapping()
    public String getLiteraturePage() {
        return "literature/literature-page";
    }


    @GetMapping("/writer/all")
    public String getAllWriters(Model model) {
        List<Writer> writerList = writerService.getAllSortedByBorn();
        List<WriterViewDTO> writerViewDTOList = writerService.getWriterViewDTOList(writerList);
        model.addAttribute("writerList", writerViewDTOList);
        return "literature/writer-all";
    }

    @GetMapping("/writer/{slug}")
    public String getWriter(@PathVariable String slug, Model model) {
        Writer bySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(bySlug);
        List<Prose> allByWriterSortedByRating = proseService.getAllByWriterSortedByRating(bySlug);
        List<ProseViewDTO> sortedProseViewDTOList = proseService.getProseViewDTOList(allByWriterSortedByRating);
        model.addAttribute("writerDTO", writerViewDTO);
        model.addAttribute("bestProseList", sortedProseViewDTOList);
        return "literature/writer";
    }

    @GetMapping("/writer/{slug}/quote/all")
    public String getWriterAllQuotes(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(writerBySlug);
        List<Quote> allByWriterSortedByYearAndPage = quoteService.getAllByWriterSortedByYearAndPage(writerBySlug);
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(allByWriterSortedByYearAndPage);
        model.addAttribute("quoteList", quoteViewDTOList);
        model.addAttribute("writerDTO", writerViewDTO);
        return "literature/writer-quote-all";
    }

    @GetMapping("/writer/{slug}/word/all")
    public String getWriterAllWords(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(writerBySlug);
        List<Word> allByWriterSortedByTitle = wordService.getAllByWriterSortedByTitle(writerBySlug);
        List<WordDTO> wordDTOList = wordService.getWordDTOList(allByWriterSortedByTitle);
        model.addAttribute("wordList", wordDTOList);
        model.addAttribute("writerDTO", writerViewDTO);
        return "literature/writer-word-all";
    }

    @GetMapping("/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAllProseSortedByCreatedDesc();
        List<ProseViewDTO> proseViewDTOList = proseService.getProseViewDTOList(proseList);
        model.addAttribute("proseList", proseViewDTOList);
        return "literature/prose-all";
    }

    @GetMapping("/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute("quoteList", quoteViewDTOList);
        return "literature/quote-all";
    }

}
