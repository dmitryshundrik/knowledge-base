package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.common.enums.EventType;
import com.dmitryshundrik.knowledgebase.model.music.*;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.common.EventService;
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
    private YearInMusicService yearInMusicService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping()
    public String getMusicPage(Model model) {
        List<YearInMusic> yearInMusicList = yearInMusicService.getAll();
        model.addAttribute("yearInMusicList", yearInMusicService.getYearInMusicViewDTOList(yearInMusicList));
        model.addAttribute("musicPeriods", musicPeriodService.getFilteredMusicPeriods());
        model.addAttribute("albumRatings", albumService.getAllYearsFromAlbums());
        model.addAttribute("classicalMusicGenres", musicGenreService.getFilteredClassicalGenres());
        model.addAttribute("contemporaryMusicGenres", musicGenreService.getFilteredContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getYearInMusic(@PathVariable String slug, Model model) {
        YearInMusic yearInMusic = yearInMusicService.getYearInMusicBySlug(slug);
        model.addAttribute("yearInMusic", yearInMusicService.getYearInMusicViewDTO(yearInMusic));
        model.addAttribute("albums", albumService.getAllAlbumsForAOTYList(yearInMusic.getYear()));
        model.addAttribute("compositions", compositionService.getAllCompositionsForSOTYList(yearInMusic.getYear()));
        return "music/year-in-music";
    }

    @GetMapping("/list-and-charts/albums-of-{year}")
    public String getAllAlbumsByYear(@PathVariable String year, Model model) {
        List<Album> allAlbumsByYear = albumService.getAllAlbumsByYear(Integer.valueOf(year));
        model.addAttribute("albums", albumService.getSortedAlbumViewDTOList(allAlbumsByYear, SortType.RATING));
        model.addAttribute("year", year);
        return "music/albums-of-year";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        model.addAttribute("eventsBCE", eventService.getAllMusicTimelineEventsBeforeCommon());
        model.addAttribute("eventsCE", eventService.getAllMusicTimelineEventsByCommonEra());
        model.addAttribute("eraTypes", EraType.values());
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
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("albums", albumService.getSortedAlbumViewDTOList(albums, SortType.RATING));
        return "music/album-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        model.addAttribute("musicianViewDTO", musicianService.getMusicianViewDTO(musicianBySlug));
        return "music/musician";
    }

    @GetMapping("/period/{periodSlug}")
    public String getPeriodBySlug(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        List<Composition> allCompositionsByPeriod = compositionService.getAllCompositionsByPeriod(musicPeriod);
        model.addAttribute("musicPeriod", musicPeriod);
        model.addAttribute("compositions", compositionService
                .getSortedCompositionViewDTOList(allCompositionsByPeriod, SortType.RATING));
        return "music/music-period";
    }

    @GetMapping("/genre/{genreSlug}")
    public String getGenreBySlug(@PathVariable String genreSlug, Model model) {
        MusicGenre musicGenre = musicGenreService.getMusicGenreBySlug(genreSlug);
        model.addAttribute("musicGenre", musicGenre);
        model.addAttribute("classicalType", MusicGenreType.CLASSICAL);
        model.addAttribute("contemporaryType", MusicGenreType.CONTEMPORARY);
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY)) {
            List<Album> albumsByGenre = albumService.getAllAlbumsByGenre(musicGenre);
            model.addAttribute("albums", albumService
                    .getSortedAlbumViewDTOList(albumsByGenre, SortType.RATING));
        }
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL)) {
            List<Composition> allCompositionsByGenre = compositionService.getAllCompositionsByGenre(musicGenre);
            model.addAttribute("compositions", compositionService
                    .getSortedCompositionViewDTOList(allCompositionsByGenre, SortType.RATING));
        }
        return "music/music-genre";
    }

}
