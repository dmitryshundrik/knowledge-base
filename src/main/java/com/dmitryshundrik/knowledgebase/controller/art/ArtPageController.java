package com.dmitryshundrik.knowledgebase.controller.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistViewDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingViewDTO;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/art")
public class ArtPageController {

    private final ArtistService artistService;

    private final PaintingService paintingService;

    public ArtPageController(ArtistService artistService, PaintingService paintingService) {
        this.artistService = artistService;
        this.paintingService = paintingService;
    }

    @GetMapping
    public String getArtPage() {
        return "art/art-page";
    }

    @GetMapping("/artist/all")
    public String getAllArtists(Model model) {
        List<Artist> artistList = artistService.getAllSortedByBorn();
        List<ArtistViewDTO> artistViewDTOList = artistService.getArtistViewDTOList(artistList);
        model.addAttribute("artistList", artistViewDTOList);
        return "art/artist-all";
    }

    @GetMapping("/artist/{artistSlug}")
    public String getArtist(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        if(artistSlug.equals("unknown") || bySlug == null) {
            return "error";
        }
        ArtistViewDTO artistViewDTO = artistService.getArtistViewDTO(bySlug);
        artistViewDTO.setPaintingList(artistViewDTO.getPaintingList().stream()
                .limit(10).collect(Collectors.toList()));
        List<Painting> bestPaintingsByArtist = paintingService.getBestPaintingsByArtist(artistService.getBySlug(artistSlug));
        model.addAttribute("artistDTO", artistViewDTO);
        model.addAttribute("bestPaintingList", bestPaintingsByArtist);
        return "art/artist";
    }

    @GetMapping("/artist/{artistSlug}/painting/all")
    public String getArtistAllPaintings(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        ArtistViewDTO artistViewDTO = artistService.getArtistViewDTO(bySlug);
        List<Painting> paintingList = paintingService.getAllByArtistSortedByYear2(bySlug);
        List<PaintingViewDTO> paintingViewDTOList = paintingService.getPaintingViewDTOList(paintingList);
        model.addAttribute("artistDTO", artistViewDTO);
        model.addAttribute("paintingList", paintingViewDTOList);
        return "art/artist-painting-all";
    }

    @GetMapping("/painting/all")
    public String getAllPainting(Model model) {
        List<Painting> paintingList = paintingService.getAllSortedByCreatedDesc();
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

    @GetMapping("/museum/all")
    public String getAllMuseums(Model model) {
        //
        return "art/museum-all";
    }

    @GetMapping("/theatre/all")
    public String getAllTheatres(Model model) {
        //
        return "art/theatre-all";
    }
}
