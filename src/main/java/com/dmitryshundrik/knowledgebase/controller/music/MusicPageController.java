package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.common.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.music.*;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.common.TimelineEventService;
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
    private TimelineEventService timelineEventService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping()
    public String getMusicPage(Model model) {
        model.addAttribute("yearsInMusic", yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute("musicPeriods", musicPeriodService.getAllSortedByStart());
        model.addAttribute("albumRatings", albumService.getAllYearsFromAlbums());
        model.addAttribute("classicalMusicGenres", musicGenreService.getFilteredClassicalGenres());
        model.addAttribute("contemporaryMusicGenres", musicGenreService.getFilteredContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getYearInMusic(@PathVariable String slug, Model model) {
        YearInMusic yearInMusic = yearInMusicService.getYearInMusicBySlug(slug);
        YearInMusic previousYear = yearInMusicService.getYearInMusicByYear(yearInMusic.getYear() - 1);
        if (previousYear != null) {
            model.addAttribute("previousYear", yearInMusicService.getYearInMusicViewDTO(previousYear));
        }
        YearInMusic nextYear = yearInMusicService.getYearInMusicByYear(yearInMusic.getYear() + 1);
        if(nextYear != null) {
            model.addAttribute("nextYear", yearInMusicService.getYearInMusicViewDTO(nextYear));
        }
        model.addAttribute("currentYear", yearInMusicService.getYearInMusicViewDTO(yearInMusic));
        model.addAttribute("albums", albumService.get10BestAlbumsByYear(yearInMusic.getYear()));
        model.addAttribute("compositions", compositionService.getAllForSOTYList(yearInMusic.getYear()));
        return "music/year-in-music";
    }

    @GetMapping("/album/top100")
    public String getTop100BestAlbums(Model model) {
        List<AlbumViewDTO> top100BestAlbums = albumService.getTop100BestAlbums();
        model.addAttribute("albums", top100BestAlbums);
        return "music/album-top100";
    }

    @GetMapping("/album/all")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAll();
        model.addAttribute("albums", albumService.getSortedAlbumViewDTOList(albums, SortType.CREATED));
        return "music/album-all";
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(Model model) {
        List<Musician> sortedMusicians = musicianService.getAllMusiciansSortedByBorn();
        List<MusicianViewDTO> musicianViewDTOList = musicianService.getMusicianViewDTOList(sortedMusicians);
        for (MusicianViewDTO musicianViewDTO : musicianViewDTOList) {
            musicianViewDTO.setMusicGenres(musicianViewDTO
                    .getMusicGenres()
                    .subList(0, Math.min(musicianViewDTO.getMusicGenres().size(), 5)));
        }
        model.addAttribute("musicians", musicianViewDTOList);
        return "music/musician-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        MusicianViewDTO musicianViewDTO = musicianService.getMusicianViewDTO(musicianBySlug);
        musicianViewDTO.setMusicPeriods(musicPeriodService.getSortedByStart(musicianViewDTO.getMusicPeriods()));
        model.addAttribute("musicianViewDTO", musicianViewDTO);
        return "music/musician";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        model.addAttribute("eventsBCE", timelineEventService.getAllMusicTimelineEventsBeforeCommon());
        model.addAttribute("eventsCE", timelineEventService.getAllMusicTimelineEventsByCommonEra());
        model.addAttribute("eraTypes", EraType.values());
        return "music/timeline-of-music";
    }

    @GetMapping("/lists-and-charts/albums-of-{year}")
    public String getAllAlbumsByYear(@PathVariable String year, Model model) {
        List<Album> allAlbumsByYear = albumService.getAllAlbumsByYear(Integer.valueOf(year));
        model.addAttribute("albums", albumService.getSortedAlbumViewDTOList(allAlbumsByYear, SortType.RATING));
        model.addAttribute("year", year);
        return "music/albums-of-year";
    }

    @GetMapping("/period/{periodSlug}")
    public String getPeriodBySlug(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        MusicPeriodViewDTO musicPeriodViewDTO = musicPeriodService.getMusicPeriodViewDTO(musicPeriod);
        List<Musician> bestMusicianByPeriod = musicianService.getBestMusicianByPeriod(musicPeriod);
        List<CompositionViewDTO> allCompositionsByPeriod = compositionService
                .getSortedCompositionViewDTOList(compositionService
                        .getAllByPeriod(musicianService
                                .getAllMusiciansByPeriod(musicPeriod)), SortType.RATING);
        model.addAttribute("musicPeriod", musicPeriodViewDTO);
        model.addAttribute("musicians", bestMusicianByPeriod);
        model.addAttribute("compositions", allCompositionsByPeriod);
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
            List<Composition> allCompositionsByGenre = compositionService.getAllByGenre(musicGenre);
            model.addAttribute("compositions", compositionService
                    .getSortedCompositionViewDTOList(allCompositionsByGenre, SortType.RATING));
        }
        return "music/music-genre";
    }

}
