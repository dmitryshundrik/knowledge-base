package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.music.*;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.service.music.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/music")
public class MusicPageController {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private SOTYListService sotyListService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping()
    public String getMusicPage(Model model) {
        model.addAttribute("SOTYLists", sotyListService.getAllSOTYLists());
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalMusicGenres", musicGenreService.getFilteredClassicalGenres());
        model.addAttribute("contemporaryMusicGenres", musicGenreService.getFilteredContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getSOTYList(@PathVariable String slug, Model model) {
        SOTYList sotyListBySlug = sotyListService.getSOTYListBySlug(slug);
        model.addAttribute("SOTYList", sotyListBySlug);
        model.addAttribute("compositions", compositionService.getAllCompositionsBySOTYList(sotyListBySlug));
        return "music/soty-list";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        return "music/timeline-of-music";
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(Model model) {
        List<Musician> sortedMusicians = musicianService.getAllMusiciansSortedByBorn();
        model.addAttribute("musicians", musicianService.getMusicianViewDTOList(sortedMusicians));
        return "music/musician-all";
    }

    @GetMapping("/album/all")
    public String getAllAlbums(Model model) {
        List<Album> sortedAlbums = albumService.getAllAlbumsSortedByRating();
        model.addAttribute("albums", albumService.getAlbumViewDTOList(sortedAlbums));
        return "music/album-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        model.addAttribute("musicianViewDTO", musicianService.getMusicianViewDTO(musicianBySlug));
        return "music/musician";
    }

    @GetMapping("/period/{slug}")
    public String getPeriodBySlug(@PathVariable String slug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getMusicPeriodBySlug(slug);
        List<Album> sortedAlbums = albumService.getAllAlbumsByPeriodSortedByRating(musicPeriod);
        List<Composition> allCompositionsByPeriod = compositionService.getAllCompositionsByPeriod(musicPeriod);
        model.addAttribute("musicPeriod", musicPeriod);
        model.addAttribute("albums", albumService.getAlbumViewDTOList(sortedAlbums));
        model.addAttribute("compostitions", compositionService.getCompositionViewDTOList(allCompositionsByPeriod));
        return "music/music-period";
    }

    @GetMapping("/genre/{slug}")
    public String getClassicalGenreBySlug(@PathVariable String slug, Model model) {
        MusicGenre musicGenre = musicGenreService.getMusicGenreBySlug(slug);
        model.addAttribute("musicGenre", musicGenre);
        model.addAttribute("classicalType", MusicGenreType.CLASSICAL);
        model.addAttribute("contemporaryType", MusicGenreType.CONTEMPORARY);
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY)) {
            model.addAttribute("albums", albumService
                    .getAlbumViewDTOList(albumService
                            .getAllAlbumsByGenreSortedByRating(musicGenre)));
        }
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL)) {
            model.addAttribute("compositions", compositionService
                    .getCompositionViewDTOList(compositionService
                            .getAllCompositionsByGenreSortedByRating(musicGenre)));
        }
        return "music/music-genre";
    }

}
