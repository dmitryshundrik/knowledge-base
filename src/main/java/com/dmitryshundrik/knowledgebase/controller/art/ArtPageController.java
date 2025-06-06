package com.dmitryshundrik.knowledgebase.controller.art;

import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
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
import static com.dmitryshundrik.knowledgebase.util.Constants.SORT_DIRECTION_ASC;
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
        List<Artist> artistList = artistService.getAllOrderByBorn();
        List<ArtistViewDto> artistDtoList = artistService.getArtistViewDtoList(artistList);
        model.addAttribute(ARTIST_LIST, artistDtoList);
        return "art/artist-all";
    }

    @GetMapping("/artist/{artistSlug}")
    public String getArtist(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        if(artistSlug.equals(UNKNOWN) || bySlug == null) {
            return "error";
        }
        ArtistViewDto artistDto = artistService.getArtistViewDto(bySlug);
        artistDto.setPaintingList(artistDto.getPaintingList().stream()
                .limit(10).toList());
        List<PaintingSimpleDto> bestPaintingsByArtist = paintingService.getBestPaintingsByArtist(bySlug);
        model.addAttribute(ARTIST, artistDto);
        model.addAttribute(PAINTING_LIST, bestPaintingsByArtist);
        return "art/artist";
    }

    @GetMapping("/artist/{artistSlug}/painting/all")
    public String getArtistAllPaintings(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        ArtistViewDto artistDto = artistService.getArtistViewDto(bySlug);
        List<Painting> paintingList = paintingService.getAllByArtistOrderByYear2(bySlug, SORT_DIRECTION_ASC);
        List<PaintingViewDto> paintingDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(ARTIST, artistDto);
        model.addAttribute(PAINTING_LIST, paintingDtoList);
        return "art/artist-painting-all";
    }

    @GetMapping("/painting/all")
    public String getAllPainting(Model model) {
        List<Painting> paintingList = paintingService.getAllOrderByCreatedDesc();
        List<PaintingViewDto> paintingDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(PAINTING_LIST, paintingDtoList);
        return "art/painting-all";
    }

    @GetMapping("/painting/top20")
    public String getTop20BestPaintings(Model model) {
        List<Painting> paintingList = paintingService.getAllTimeBestPaintings();
        List<PaintingViewDto> paintingDtoList = paintingService.getPaintingViewDtoList(paintingList);
        model.addAttribute(PAINTING_LIST, paintingDtoList);
        return "art/painting-top20";
    }

    @GetMapping("/ballet/ballet-dancers-top-10")
    public String getTop10BalletDancers() {
        return "art/ballet-dancers-top-10";
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
