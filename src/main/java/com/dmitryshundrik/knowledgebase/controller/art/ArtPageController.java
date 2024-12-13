package com.dmitryshundrik.knowledgebase.controller.art;

import com.dmitryshundrik.knowledgebase.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Controller
@RequestMapping("/art")
@RequiredArgsConstructor
public class ArtPageController {

    private final ArtistService artistService;

    private final PaintingService paintingService;

    @GetMapping
    public String getArtPage() {
        return "art/art-page";
    }

    @GetMapping("/artist/all")
    public String getAllArtists(Model model) {
        List<Artist> artistList = artistService.getAllSortedByBorn();
        List<ArtistViewDto> artistViewDtoList = artistService.getArtistViewDtoList(artistList);
        model.addAttribute("artistList", artistViewDtoList);
        return "art/artist-all";
    }

    @GetMapping("/artist/{artistSlug}")
    public String getArtist(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        if(artistSlug.equals(UNKNOWN) || bySlug == null) {
            return "error";
        }
        ArtistViewDto artistViewDto = artistService.getArtistViewDto(bySlug);
        artistViewDto.setPaintingList(artistViewDto.getPaintingList().stream()
                .limit(10).toList());
        List<Painting> bestPaintingsByArtist = paintingService.getBestPaintingsByArtist(artistService.getBySlug(artistSlug));
        model.addAttribute("artist", artistViewDto);
        model.addAttribute("bestPaintingList", bestPaintingsByArtist);
        return "art/artist";
    }

    @GetMapping("/artist/{artistSlug}/painting/all")
    public String getArtistAllPaintings(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        ArtistViewDto artistViewDto = artistService.getArtistViewDto(bySlug);
        List<Painting> paintingList = paintingService.getAllByArtistSortedByYear2(bySlug);
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute("artist", artistViewDto);
        model.addAttribute("paintingList", paintingViewDtoList);
        return "art/artist-painting-all";
    }

    @GetMapping("/painting/all")
    public String getAllPainting(Model model) {
        List<Painting> paintingList = paintingService.getAllSortedByCreatedDesc();
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute("paintingList", paintingViewDtoList);
        return "art/painting-all";
    }

    @GetMapping("/painting/top20")
    public String getTop20BestPaintings(Model model) {
        List<Painting> paintingList = paintingService.getAllTimeBestPaintings();
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute("paintingList", paintingViewDtoList);
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
