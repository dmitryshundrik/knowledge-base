package com.dmitryshundrik.knowledgebase.controller.literature;

import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDTO;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WordService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
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
        List<WriterViewDTO> writerDtoList = writerService.getWriterViewDTOList(writerList);
        model.addAttribute(WRITER_LIST, writerDtoList);
        return "literature/writer-all";
    }

    @GetMapping("/writer/{slug}")
    public String getWriter(@PathVariable String slug, Model model) {
        Writer bySlug = writerService.getBySlug(slug);
        WriterViewDTO writerDto = writerService.getWriterViewDTO(bySlug);
        List<Prose> first5ProseByWriter = proseService.getFirst5ByWriterSortedByRating(bySlug);
        List<ProseViewDTO> first5ProseByWriterViewDtoList = proseService.getProseViewDTOList(first5ProseByWriter);
        model.addAttribute(WRITER, writerDto);
        model.addAttribute(PROSE_LIST, first5ProseByWriterViewDtoList);
        return "literature/writer";
    }

    @GetMapping("/writer/{slug}/quote/all")
    public String getWriterAllQuotes(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerDto = writerService.getWriterViewDTO(writerBySlug);
        List<Quote> allByWriterSortedByCreatedDesc = quoteService.getAllByWriterSortedByCreatedDesc(writerBySlug);
        List<QuoteViewDTO> quoteDtoList = quoteService.getQuoteViewDTOList(allByWriterSortedByCreatedDesc);
        model.addAttribute(QUOTE_LIST, quoteDtoList);
        model.addAttribute(WRITER, writerDto);
        return "literature/writer-quote-all";
    }

    @GetMapping("/writer/{slug}/word/all")
    public String getWriterAllWords(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDTO writerDto = writerService.getWriterViewDTO(writerBySlug);
        List<Word> allByWriterSortedByTitle = wordService.getAllByWriterSortedByTitle(writerBySlug);
        List<WordDTO> wordDtoList = wordService.getWordDTOList(allByWriterSortedByTitle);
        model.addAttribute(WORD_LIST, wordDtoList);
        model.addAttribute(WRITER, writerDto);
        return "literature/writer-word-all";
    }

    @GetMapping("/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAllSortedByCreatedDesc();
        List<ProseViewDTO> proseDtoList = proseService.getProseViewDTOList(proseList);
        model.addAttribute(PROSE_LIST, proseDtoList);
        return "literature/prose-all";
    }

    @GetMapping("/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteDtoList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute(QUOTE_LIST, quoteDtoList);
        return "literature/quote-all";
    }

    @GetMapping("/literature-resources")
    public String getLiteratureResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.LITERATURE);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "literature/literature-resources";
    }
}
