package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.util.enums.Gender;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.dto.literature.WriterCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.literature.WriterViewDTO;
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
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class WriterManagementController {

    private final WriterService writerService;

    @GetMapping("/management/writer/all")
    public String getAllWriters(Model model) {
        List<Writer> writerList = writerService.getAllSortedByCreatedDesc();
        List<WriterViewDTO> writerViewDTOList = writerService.getWriterViewDTOList(writerList);
        model.addAttribute("writerList", writerViewDTOList);
        return "management/literature/writer-archive";
    }

    @GetMapping("/management/writer/create")
    public String getWriterCreate(Model model) {
        WriterCreateEditDTO writerCreateEditDTO = new WriterCreateEditDTO();
        model.addAttribute("writerDTO", writerCreateEditDTO);
        model.addAttribute("genders", Gender.values());
        return "management/literature/writer-create";
    }

    @PostMapping("/management/writer/create")
    public String postWriterCreate(@Valid @ModelAttribute("writerDTO") WriterCreateEditDTO writerDTO, BindingResult bindingResult,
                                   Model model) {
        String error = writerService.writerSlugIsExist(writerDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            model.addAttribute("genders", Gender.values());
            return "management/literature/writer-create";
        }
        String writerSlug = writerService.createWriter(writerDTO).getSlug();
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @GetMapping("/management/writer/edit/{writerSlug}")
    public String getWriterEdit(@PathVariable String writerSlug, Model model) {
        Writer bySlug = writerService.getBySlug(writerSlug);
        WriterCreateEditDTO writerCreateEditDTO = writerService.getWriterCreateEditDTO(bySlug);
        model.addAttribute("writerDTO", writerCreateEditDTO);
        model.addAttribute("genders", Gender.values());
        return "management/literature/writer-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}")
    public String putWriterEdit(@PathVariable String writerSlug,
                                @ModelAttribute("writerDTO") WriterCreateEditDTO writerDTO) {
        String writerDTOSlug = writerService.updateWriter(writerSlug, writerDTO).getSlug();
        return "redirect:/management/writer/edit/" + writerDTOSlug;
    }

    @PostMapping("/management/writer/edit/{writerSlug}/image/upload")
    public String postUploadWriterImage(@PathVariable String writerSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        writerService.updateWriterImageBySlug(writerSlug, bytes);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/image/delete")
    public String deleteWriterImage(@PathVariable String writerSlug) {
        writerService.deleteWriterImage(writerSlug);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/writer/delete/{writerSlug}")
    public String deleteWriterBySlug(@PathVariable String writerSlug) {
        writerService.deleteWriterBySlug(writerSlug);
        return "redirect:/management/writer/all";
    }
}
