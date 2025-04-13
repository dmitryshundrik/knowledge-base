package com.dmitryshundrik.knowledgebase.controller.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.common.Resource;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import com.dmitryshundrik.knowledgebase.util.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.PAINTING_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Controller
@RequestMapping("/art")
@RequiredArgsConstructor
public class ArtPageController {

    private final ArtistService artistService;

    private final PaintingService paintingService;

    private final ResourcesService resourcesService;

    @GetMapping
    public String getArtPage() {
        return "art/art-page";
    }

    @GetMapping("/artist/all")
    public String getAllArtists(Model model) {
        List<Artist> artistList = artistService.getAllSortedByBorn();
        List<ArtistViewDto> artistViewDtoList = artistService.getArtistViewDtoList(artistList);
        model.addAttribute(ARTIST_LIST, artistViewDtoList);
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
        model.addAttribute(ARTIST, artistViewDto);
        model.addAttribute(PAINTING_LIST, bestPaintingsByArtist);
        return "art/artist";
    }

    @GetMapping("/artist/{artistSlug}/painting/all")
    public String getArtistAllPaintings(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        ArtistViewDto artistViewDto = artistService.getArtistViewDto(bySlug);
        List<Painting> paintingList = paintingService.getAllByArtistSortedByYear2(bySlug);
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(ARTIST, artistViewDto);
        model.addAttribute(PAINTING_LIST, paintingViewDtoList);
        return "art/artist-painting-all";
    }

    @GetMapping("/painting/all")
    public String getAllPainting(Model model) {
        List<Painting> paintingList = paintingService.getAllSortedByCreatedDesc();
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(PAINTING_LIST, paintingViewDtoList);
        return "art/painting-all";
    }

    @GetMapping("/painting/top20")
    public String getTop20BestPaintings(Model model) {
        List<Painting> paintingList = paintingService.getAllTimeBestPaintings();
        List<PaintingViewDto> paintingViewDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(PAINTING_LIST, paintingViewDtoList);
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

    @GetMapping("/art-resources")
    public String getArtResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.ART);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "art/art-resources";
    }
}
