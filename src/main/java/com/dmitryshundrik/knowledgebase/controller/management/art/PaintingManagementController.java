package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import jakarta.validation.Valid;
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
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.PAINTING;
import static com.dmitryshundrik.knowledgebase.util.Constants.PAINTING_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Controller
@RequiredArgsConstructor
public class PaintingManagementController {

    private final PaintingService paintingService;

    private final ArtistService artistService;

    @GetMapping("/management/painting/all")
    public String getAllPaintings(Model model) {
        List<Painting> paintingList = paintingService.getAllOrderByCreatedDesc();
        List<PaintingViewDto> paintingDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(PAINTING_LIST, paintingDtoList);
        return "management/art/painting-archive";
    }

    @GetMapping("management/painting/create")
    public String getAnonymousPaintingCreate(Model model) {
        PaintingCreateEditDto paintingDto = new PaintingCreateEditDto();
        paintingDto.setArtistNickname("Unknown");
        paintingDto.setArtistSlug(UNKNOWN);
        model.addAttribute(PAINTING, paintingDto);
        return "management/art/painting-create";
    }

    @PostMapping("management/painting/create")
    public String postAnonymousPaintingCreate(@Valid @ModelAttribute(PAINTING) PaintingCreateEditDto paintingDto,
                                              BindingResult bindingResult, Model model) {
        String error = paintingService.isSlugExists(paintingDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/art/painting-create";
        }
        Artist unknown = artistService.getBySlug("unknown");
        String paintingDtoSlug = paintingService
                .createPainting(unknown, paintingDto).getSlug();
        return "redirect:/management/painting/edit/" + paintingDtoSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/create")
    public String getPaintingCreate(Model model, @PathVariable String artistSlug) {
        PaintingCreateEditDto paintingDto = new PaintingCreateEditDto();
        paintingDto.setArtistNickname(artistService.getBySlug(artistSlug).getNickName());
        paintingDto.setArtistSlug(artistSlug);
        model.addAttribute(PAINTING, paintingDto);
        return "management/art/painting-create";
    }

    @PostMapping("/management/artist/edit/{artistSlug}/painting/create")
    public String postPaintingCreate(@Valid @ModelAttribute(PAINTING) PaintingCreateEditDto paintingDto,
                                     BindingResult bindingResult, Model model, @PathVariable String artistSlug) {
        String error = paintingService.isSlugExists(paintingDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/art/painting-create";
        }
        String paintingDTOSlug = paintingService
                .createPainting(artistService.getBySlug(artistSlug), paintingDto).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingDTOSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}")
    public String getPaintingEdit(@PathVariable String artistSlug,
                                  @PathVariable String paintingSlug,
                                  Model model) {
        Painting painting = paintingService.getBySlug(paintingSlug);
        PaintingCreateEditDto paintingDto = paintingService.getArtistCreateEditDto(painting);
        model.addAttribute(PAINTING, paintingDto);
        return "management/art/painting-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}/painting/edit/{paintingSlug}")
    public String putPaintingEdit(@PathVariable String artistSlug,
                                  @PathVariable String paintingSlug,
                                  @ModelAttribute(PAINTING) PaintingCreateEditDto paintingDTO) {
        String paintingDtoSlug = paintingService.updatePainting(paintingSlug, paintingDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug + "/painting/edit/" + paintingDtoSlug;
    }

    @DeleteMapping("/management/artist/edit/{artistSlug}/painting/delete/{paintingSlug}")
    public String deletePaintingBySlug(@PathVariable String artistSlug, @PathVariable String paintingSlug) {
        paintingService.deletePaintingBySlug(paintingSlug);
        return "redirect:/management/painting/all";
    }
}
