package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.dto.literature.ProseCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.literature.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.PROSE;
import static com.dmitryshundrik.knowledgebase.util.Constants.PROSE_LIST;

@Controller
@RequiredArgsConstructor
public class ProseManagementController {

    private final ProseService proseService;

    private final WriterService writerService;

    @GetMapping("/management/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAllProseSortedByCreatedDesc();
        List<ProseViewDTO> proseViewDTOList = proseService.getProseViewDTOList(proseList);
        model.addAttribute(PROSE_LIST, proseViewDTOList);
        return "management/literature/prose-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/prose/create")
    public String getProseCreate(@PathVariable String writerSlug, Model model) {
        ProseCreateEditDTO proseDTO = new ProseCreateEditDTO();
        proseDTO.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        proseDTO.setWriterSlug(writerSlug);
        model.addAttribute(PROSE, proseDTO);
        return "management/literature/prose-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/prose/create")
    public String postProseCreate(@PathVariable String writerSlug,
                                  @ModelAttribute(PROSE) ProseCreateEditDTO proseDTO, BindingResult bindingResult,
                                  Model model) {
        String error = proseService.proseSlugIsExist(proseDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            proseDTO.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
            proseDTO.setWriterSlug(writerSlug);
            model.addAttribute(PROSE, proseDTO);
            return "management/literature/prose-create";
        }
        String proseSlug = proseService.createProse(writerService.getBySlug(writerSlug), proseDTO).getSlug();
        return "redirect:/management/writer/edit/" + writerSlug + "/prose/edit/" + proseSlug;
    }

    @GetMapping("/management/writer/edit/{writerSlug}/prose/edit/{proseSlug}")
    public String getProseEdit(@PathVariable String writerSlug, @PathVariable String proseSlug, Model model) {
        Prose bySlug = proseService.getBySlug(proseSlug);
        ProseCreateEditDTO proseCreateEditDTO = proseService.getProseCreateEditDTO(bySlug);
        model.addAttribute(PROSE, proseCreateEditDTO);
        return "management/literature/prose-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/prose/edit/{proseSlug}")
    public String putProseEdit(@PathVariable String writerSlug, @PathVariable String proseSlug,
                               @ModelAttribute(PROSE) ProseCreateEditDTO proseDTO) {
        Prose bySlug = proseService.getBySlug(proseSlug);
        String proseDTOSlug = proseService.updateProse(bySlug, proseDTO).getSlug();
        return "redirect:/management/writer/edit/" + writerSlug + "/prose/edit/" + proseDTOSlug;
    }

    @PostMapping("/management/writer/edit/{writerSlug}/prose/edit/{proseSlug}/schema/upload")
    public String postUploadSynopsisSchema(@PathVariable String writerSlug, @PathVariable String proseSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        proseService.updateSynopsisSchemaBySlug(proseSlug, bytes);
        return "redirect:/management/writer/edit/" + writerSlug + "/prose/edit/" + proseSlug;
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/prose/edit/{proseSlug}/schema/delete")
    public String deleteSynopsisSchema(@PathVariable String writerSlug, @PathVariable String proseSlug) {
        proseService.deleteSynopsisSchema(proseSlug);
        return "redirect:/management/writer/edit/" + writerSlug + "/prose/edit/" + proseSlug;
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/prose/delete/{proseSlug}")
    public String deleteWritersProseBySlug(@PathVariable String writerSlug, @PathVariable String proseSlug) {
        Prose bySlug = proseService.getBySlug(proseSlug);
        bySlug.getQuoteList().forEach(quote -> quote.setProse(null));
        proseService.deleteProse(proseSlug);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/prose/delete/{proseSlug}")
    public String deleteProseBySlug(@PathVariable String proseSlug) {
        Prose bySlug = proseService.getBySlug(proseSlug);
        bySlug.getQuoteList().forEach(quote -> quote.setProse(null));
        proseService.deleteProse(proseSlug);
        return "redirect:/management/prose/all";
    }
}
