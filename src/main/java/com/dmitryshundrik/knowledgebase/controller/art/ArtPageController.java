package com.dmitryshundrik.knowledgebase.controller.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.art.dto.PainterViewDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingViewDTO;
import com.dmitryshundrik.knowledgebase.service.art.PainterService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/art")
public class ArtPageController {

    @Autowired
    private PainterService painterService;

    @Autowired
    private PaintingService paintingService;

    @GetMapping()
    public String getArtPage() {
        return "art/art-page";
    }

    @GetMapping("/painter/all")
    public String getAllPainters(Model model) {
        List<Painter> painterList = painterService.getAll();
        List<PainterViewDTO> painterViewDTOList = painterService.getPainterViewDTOList(painterList);
        model.addAttribute("painterList", painterViewDTOList);
        return "art/painter-all";
    }

    @GetMapping("/painter/{painterSlug}")
    public String getPainter(@PathVariable String painterSlug, Model model) {
        Painter bySlug = painterService.getBySlug(painterSlug);
        PainterViewDTO painterViewDTO = painterService.getPainterViewDTO(bySlug);
        painterViewDTO.setPaintingList(painterViewDTO.getPaintingList().stream()
                .limit(10).collect(Collectors.toList()));
        List<Painting> bestPaintingsByPainter = paintingService.getBestPaintingsByPainter(painterService.getBySlug(painterSlug));
        model.addAttribute("painterDTO", painterViewDTO);
        model.addAttribute("bestPaintingList", bestPaintingsByPainter);
        return "art/painter";
    }

    @GetMapping("/painter/{painterSlug}/painting/all")
    public String getPainterAllPaintings(@PathVariable String painterSlug, Model model) {
        Painter bySlug = painterService.getBySlug(painterSlug);
        PainterViewDTO painterViewDTO = painterService.getPainterViewDTO(bySlug);
        List<Painting> paintingList = bySlug.getPaintingList();
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(paintingList);
        model.addAttribute("painterDTO", painterViewDTO);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "art/painter-painting-all";
    }

    @GetMapping("/painting/all")
    public String getAllPainting(Model model) {
        List<Painting> paintingList = paintingService.getAll();
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(paintingList);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "art/painting-all";
    }

    @GetMapping("/painting/top20")
    public String getTop20BestPaintings(Model model) {
        List<Painting> paintingList = paintingService.getAllTimeBestPaintings();
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(paintingList);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "art/painting-top20";
    }

    @GetMapping("/gallery/all")
    public String getAllGalleries(Model model) {
        //
        return "art/gallery-all";
    }

    @GetMapping("/theatre/all")
    public String getAllTheatres(Model model) {
        //
        return "art/theatre-all";
    }
}
