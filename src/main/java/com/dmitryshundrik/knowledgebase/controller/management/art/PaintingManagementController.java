package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingViewDTO;
import com.dmitryshundrik.knowledgebase.service.art.PainterService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PaintingManagementController {

    @Autowired
    private PaintingService paintingService;

    @Autowired
    private PainterService painterService;

    @GetMapping("/management/painting/all")
    public String getAllPaintings(Model model) {
        List<Painting> allSortedByCreatedDesc = paintingService.getAllSortedByCreatedDesc();
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(allSortedByCreatedDesc);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "management/art/painting-archive";
    }

    @GetMapping("/management/painter/edit/{painterSlug}/painting/create")
    public String getPaintingCreate(Model model, @PathVariable String painterSlug) {
        PaintingCreateEditDTO paintingDTO = new PaintingCreateEditDTO();
        paintingDTO.setPainterNickname(painterService.getBySlug(painterSlug).getNickName());
        paintingDTO.setPainterSlug(painterSlug);
        model.addAttribute("paintingDTO", paintingDTO);
        return "management/art/painting-create";
    }

    @PostMapping("/management/painter/edit/{painterSlug}/painting/create")
    public String postPaintingCreate(@Valid @ModelAttribute("paintingDTO") PaintingCreateEditDTO paintingDTO,
                                     BindingResult bindingResult, Model model, @PathVariable String painterSlug) {
        String error = paintingService.paintingSlugIsExist(paintingDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            return "management/art/painting-create";
        }
        String paintingDTOSlug = paintingService
                .createPainting(painterService.getBySlug(painterSlug), paintingDTO).getSlug();
        return "redirect:/management/painter/edit/" + painterSlug + "/painting/edit/" + paintingDTOSlug;
    }

    @GetMapping("/management/painter/edit/{painterSlug}/painting/edit/{paintingSlug}")
    public String getPaintingEdit(@PathVariable String painterSlug,
                                  @PathVariable String paintingSlug,
                                  Model model) {
        Painting bySlug = paintingService.getBySlug(paintingSlug);
        PaintingCreateEditDTO paintingDTO = paintingService.getPainterCreateEditDTO(bySlug);
        model.addAttribute("paintingDTO", paintingDTO);
        return "management/art/painting-edit";
    }

    @PutMapping("/management/painter/edit/{painterSlug}/painting/edit/{paintingSlug}")
    public String putPaintingEdit(@PathVariable String painterSlug,
                                  @PathVariable String paintingSlug,
                                  @ModelAttribute("paintingDTO") PaintingCreateEditDTO paintingDTO) {
        String paintingDTOSlug = paintingService.updatePainting(paintingSlug, paintingDTO).getSlug();
        return "redirect:/management/painter/edit/" + painterSlug + "/painting/edit/" + paintingDTOSlug;
    }

    @DeleteMapping("/management/painter/edit/{painterSlug}/painting/delete/{paintingSlug}")
    public String deletePaintingBySlug(@PathVariable String painterSlug, @PathVariable String paintingSlug) {
        paintingService.deletePaintingBySlug(paintingSlug);
        return "redirect:/management/painter/edit/" + painterSlug;
    }

}
