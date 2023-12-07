package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import com.dmitryshundrik.knowledgebase.model.art.dto.PainterCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PainterViewDTO;
import com.dmitryshundrik.knowledgebase.service.art.PainterService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class PainterManagementController {

    @Autowired
    private PainterService painterService;

    @GetMapping("/management/painter/all")
    public String getAllPainters(Model model) {
        List<Painter> allSortedByCreatedDesc = painterService.getAllSortedByCreatedDesc();
        List<PainterViewDTO> painterViewDTOList = painterService.getPainterViewDTOList(allSortedByCreatedDesc);
        model.addAttribute("painterList", painterViewDTOList);
        return "management/art/painter-archive";
    }

    @GetMapping("/management/painter/create")
    public String getPainterCreate(Model model) {
        PainterCreateEditDTO painterCreateEditDTO = new PainterCreateEditDTO();
        model.addAttribute("painterDTO", painterCreateEditDTO);
        return "management/art/painter-create";
    }

    @PostMapping("/management/painter/create")
    public String postPainterCreate(@Valid @ModelAttribute("painterDTO") PainterCreateEditDTO painterDTO,
                                    BindingResult bindingResult, Model model) {
        String error = painterService.painterSlugIsExist(painterDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            return "management/art/painter-create";
        }
        String painterSlug = painterService.createPainter(painterDTO).getSlug();
        return "redirect:/management/painter/edit/" + painterSlug;
    }

    @GetMapping("/management/painter/edit/{painterSlug}")
    public String getPainterEdit(@PathVariable String painterSlug, Model model) {
        Painter bySlug = painterService.getBySlug(painterSlug);
        PainterCreateEditDTO painterCreateEditDTO = painterService.getPainterCreateEditDTO(bySlug);
        model.addAttribute("painterDTO", painterCreateEditDTO);
        return "management/art/painter-edit";
    }

    @PutMapping("/management/painter/edit/{painterSlug}")
    public String putPainterEdit(@PathVariable String painterSlug,
                                 @ModelAttribute("painterDTO") PainterCreateEditDTO painterDTO) {
        String painterDTOSlug = painterService.updatePainter(painterSlug, painterDTO).getSlug();
        return "redirect:/management/painter/edit/" + painterDTOSlug;
    }

    @PostMapping("/management/painter/edit/{painterSlug}/image/upload")
    public String postUploadPainterImage(@PathVariable String painterSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        painterService.updatePainterImageBySlug(painterSlug, bytes);
        return "redirect:/management/painter/edit/" + painterSlug;
    }

    @DeleteMapping("/management/painter/edit/{painterSlug}/image/delete")
    public String deletePainterImage(@PathVariable String painterSlug) {
        painterService.deletePainterImage(painterSlug);
        return "redirect:/management/painter/edit/" + painterSlug;
    }

    @DeleteMapping("/management/painter/delete/{painterSlug}")
    public String deletePainterBySlug(@PathVariable String painterSlug) {
        painterService.deletePainterBySlug(painterSlug);
        return "redirect:/management/painter/all";
    }
}
