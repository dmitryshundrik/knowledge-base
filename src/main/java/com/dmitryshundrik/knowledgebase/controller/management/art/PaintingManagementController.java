package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingViewDTO;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaintingManagementController {

    private final PaintingService paintingService;

    private final ArtistService artistService;

    @GetMapping("/management/painting/all")
    public String getAllPaintings(Model model) {
        List<Painting> allSortedByCreatedDesc = paintingService.getAllSortedByCreatedDesc();
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(allSortedByCreatedDesc);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "management/art/painting-archive";
    }

    @GetMapping("management/painting/create")
    public String getAnonymousPaintingCreate(Model model) {
        PaintingCreateEditDTO paintingDTO = new PaintingCreateEditDTO();
        paintingDTO.setArtistNickname("Unknown");
        paintingDTO.setArtistSlug("unknown");
        model.addAttribute("paintingDTO", paintingDTO);
        return "management/art/painting-create";
    }

    @PostMapping("management/painting/create")
    public String postAnonymousPaintingCreate(@Valid @ModelAttribute("paintingDTO") PaintingCreateEditDTO paintingDTO,
                                              BindingResult bindingResult, Model model) {
        String error = paintingService.paintingSlugIsExist(paintingDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            return "management/art/painting-create";
        }
        Artist unknown = artistService.getBySlug("unknown");
        String paintingDTOSlug = paintingService
                .createPainting(unknown, paintingDTO).getSlug();
        return "redirect:/management/painting/edit/" + paintingDTOSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/create")
    public String getPaintingCreate(Model model, @PathVariable String artistSlug) {
        PaintingCreateEditDTO paintingDTO = new PaintingCreateEditDTO();
        paintingDTO.setArtistNickname(artistService.getBySlug(artistSlug).getNickName());
        paintingDTO.setArtistSlug(artistSlug);
        model.addAttribute("paintingDTO", paintingDTO);
        return "management/art/painting-create";
    }

    @PostMapping("/management/artist/edit/{artistSlug}/painting/create")
    public String postPaintingCreate(@Valid @ModelAttribute("paintingDTO") PaintingCreateEditDTO paintingDTO,
                                     BindingResult bindingResult, Model model, @PathVariable String artistSlug) {
        String error = paintingService.paintingSlugIsExist(paintingDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            return "management/art/painting-create";
        }
        String paintingDTOSlug = paintingService
                .createPainting(artistService.getBySlug(artistSlug), paintingDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingDTOSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}")
    public String getPaintingEdit(@PathVariable String artistSlug,
                                  @PathVariable String paintingSlug,
                                  Model model) {
        Painting bySlug = paintingService.getBySlug(paintingSlug);
        PaintingCreateEditDTO paintingDTO = paintingService.getArtistCreateEditDTO(bySlug);
        model.addAttribute("paintingDTO", paintingDTO);
        return "management/art/painting-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}")
    public String putPaintingEdit(@PathVariable String artistSlug,
                                  @PathVariable String paintingSlug,
                                  @ModelAttribute("paintingDTO") PaintingCreateEditDTO paintingDTO) {
        String paintingDTOSlug = paintingService.updatePainting(paintingSlug, paintingDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingDTOSlug;
    }

    @DeleteMapping("/management/artist/edit/{artistSlug}/painting/delete/{paintingSlug}")
    public String deletePaintingBySlug(@PathVariable String artistSlug, @PathVariable String paintingSlug) {
        paintingService.deletePaintingBySlug(paintingSlug);
        return "redirect:/management/artist/edit/" + artistSlug;
    }
}
