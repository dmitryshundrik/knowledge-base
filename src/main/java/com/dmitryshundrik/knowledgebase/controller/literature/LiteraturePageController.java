package com.dmitryshundrik.knowledgebase.controller.literature;

import com.dmitryshundrik.knowledgebase.model.entity.common.Resource;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDTO;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.util.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.PROSE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.QUOTE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.WORD_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER_LIST;

@Controller
@RequestMapping("/literature")
@RequiredArgsConstructor
public class LiteraturePageController {

    private final WriterService writerService;

    private final ProseService proseService;

    private final QuoteService quoteService;

    private final WordService wordService;

    private final ResourcesService resourcesService;

    @GetMapping
    public String getLiteraturePage() {
        return "literature/literature-page";
    }


    @GetMapping("/writer/all")
    public String getAllWriters(Model model) {
        List<Writer> writerList = writerService.getAllSortedByBorn();
        List<WriterViewDTO> writerViewDTOList = writerService.getWriterViewDTOList(writerList);
        model.addAttribute(WRITER_LIST, writerViewDTOList);
        return "literature/writer-all";
    }

    @GetMapping("/writer/{slug}")
    public String getWriter(@PathVariable String slug, Model model) {
        Writer bySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(bySlug);
        List<Prose> first5ProseByWriter = proseService.getFirst5ByWriterSortedByRating(bySlug);
        List<ProseViewDTO> first5ProseByWriterViewDTOList = proseService.getProseViewDTOList(first5ProseByWriter);
        model.addAttribute(WRITER, writerViewDTO);
        model.addAttribute(PROSE_LIST, first5ProseByWriterViewDTOList);
        return "literature/writer";
    }

    @GetMapping("/writer/{slug}/quote/all")
    public String getWriterAllQuotes(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(writerBySlug);
        List<Quote> allByWriterSortedByCreatedDesc = quoteService.getAllByWriterSortedByCreatedDesc(writerBySlug);
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(allByWriterSortedByCreatedDesc);
        model.addAttribute(QUOTE_LIST, quoteViewDTOList);
        model.addAttribute(WRITER, writerViewDTO);
        return "literature/writer-quote-all";
    }

    @GetMapping("/writer/{slug}/word/all")
    public String getWriterAllWords(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(writerBySlug);
        List<Word> allByWriterSortedByTitle = wordService.getAllByWriterSortedByTitle(writerBySlug);
        List<WordDTO> wordDTOList = wordService.getWordDTOList(allByWriterSortedByTitle);
        model.addAttribute(WORD_LIST, wordDTOList);
        model.addAttribute(WRITER, writerViewDTO);
        return "literature/writer-word-all";
    }

    @GetMapping("/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAllSortedByCreatedDesc();
        List<ProseViewDTO> proseViewDTOList = proseService.getProseViewDTOList(proseList);
        model.addAttribute(PROSE_LIST, proseViewDTOList);
        return "literature/prose-all";
    }

    @GetMapping("/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute(QUOTE_LIST, quoteViewDTOList);
        return "literature/quote-all";
    }

    @GetMapping("/literature-resources")
    public String getLiteratureResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.LITERATURE);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "literature/literature-resources";
    }
}
