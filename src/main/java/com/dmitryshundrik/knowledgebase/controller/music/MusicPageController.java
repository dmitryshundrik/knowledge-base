package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbums;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtists;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.service.client.LastFmService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDTO;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianViewDTO;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.core.TimelineEventService;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM_RATINGS;
import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_TYPE;
import static com.dmitryshundrik.knowledgebase.util.Constants.COMPOSITION_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_TYPE;
import static com.dmitryshundrik.knowledgebase.util.Constants.CURRENT_YEAR;
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE;
import static com.dmitryshundrik.knowledgebase.util.Constants.ERA_TYPE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.EVENTS_BEFORE_CE;
import static com.dmitryshundrik.knowledgebase.util.Constants.EVENTS_CE;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_GENRE;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.NEXT_YEAR;
import static com.dmitryshundrik.knowledgebase.util.Constants.PREVIOUS_YEAR;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_IN_MUSIC;

@Controller
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicPageController {

    private final MusicianService musicianService;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final YearInMusicService yearInMusicService;

    private final TimelineEventService timelineEventService;

    private final MusicPeriodService musicPeriodService;

    private final MusicGenreService musicGenreService;

    private final ResourcesService resourcesService;

    private final LastFmService lastFmService;

    @GetMapping
    public String getMusicPage(Model model) {
        model.addAttribute(YEAR_IN_MUSIC, yearInMusicService.getSortedYearInMusicViewDTOList());
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllSortedByStart());
        model.addAttribute(ALBUM_RATINGS, albumService.getAllYearsFromAlbums());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getFilteredClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getFilteredContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getYearInMusic(@PathVariable String slug, Model model) {
        YearInMusic yearInMusic = yearInMusicService.getYearInMusicBySlug(slug);
        YearInMusic previousYear = yearInMusicService.getYearInMusicByYear(yearInMusic.getYear() - 1);
        if (previousYear != null) {
            model.addAttribute(PREVIOUS_YEAR, yearInMusicService.getYearInMusicViewDTO(previousYear));
        }
        YearInMusic nextYear = yearInMusicService.getYearInMusicByYear(yearInMusic.getYear() + 1);
        if (nextYear != null) {
            model.addAttribute(NEXT_YEAR, yearInMusicService.getYearInMusicViewDTO(nextYear));
        }
        model.addAttribute(CURRENT_YEAR, yearInMusicService.getYearInMusicViewDTO(yearInMusic));
        model.addAttribute(ALBUM_LIST, albumService.get10BestAlbumsByYear(yearInMusic.getYear()));
        model.addAttribute(COMPOSITION_LIST, compositionService.getAllForSOTYList(yearInMusic.getYear()));
        return "music/year-in-music";
    }

    @GetMapping("/album/top100")
    public String getTop100BestAlbums(Model model) {
        List<AlbumViewDTO> top100BestAlbums = albumService.getTop100BestAlbums();
        model.addAttribute(ALBUM_LIST, top100BestAlbums);
        return "music/album-top100";
    }

    @GetMapping("/composition/top100")
    public String getTop100BestClassicalCompositions(Model model) {
        List<Composition> top100BestClassicalCompositions = compositionService
                .getTop100ByClassicalGenreOrderedByRatingDesc();
        List<CompositionViewDTO> compositionViewDTOList = compositionService.getCompositionViewDTOList(top100BestClassicalCompositions);
        model.addAttribute(COMPOSITION_LIST, compositionViewDTOList);
        return "music/classical-composition-top100";
    }

    @GetMapping("/album/all")
    public String getAllAlbums(Model model) {
        List<Album> albums = albumService.getAll();
        model.addAttribute(ALBUM_LIST, albumService.getSortedAlbumViewDTOList(albums, SortType.CREATED));
        return "music/album-all";
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(Model model) {
        List<MusicianAllPageResponseDto> musicians = musicianService.getMusicianAllPageResponseDtoSortedByBornAndFounded();
        model.addAttribute(MUSICIAN_LIST, musicians);
        return "music/musician-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        MusicianViewDTO musicianViewDTO = musicianService.getMusicianViewDTO(musicianBySlug);
        musicianViewDTO.setMusicPeriods(musicPeriodService.getSortedByStart(musicianBySlug.getMusicPeriods()));
        model.addAttribute(MUSICIAN, musicianViewDTO);
        return "music/musician";
    }

    @GetMapping("/timeline-of-music")
    public String getTimelineOfMusic(Model model) {
        model.addAttribute(EVENTS_BEFORE_CE, timelineEventService.getAllMusicTimelineEventsBeforeCommon());
        model.addAttribute(EVENTS_CE, timelineEventService.getAllMusicTimelineEventsByCommonEra());
        model.addAttribute(ERA_TYPE_LIST, EraType.values());
        return "music/timeline-of-music";
    }

    @GetMapping("/music-resources")
    public String getMusicResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.MUSIC);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "music/music-resources";
    }

    @GetMapping("/listening-stats")
    public String getListeningStats(Model model) {
        TopArtists topArtists = lastFmService.processTopArtist();
        TopAlbums topAlbums = lastFmService.processTopAlbums();
        model.addAttribute("artistList", topArtists.getArtists());
        model.addAttribute("albumsList", topAlbums.getAlbums());
        return "music/listening-stats";
    }

    @GetMapping("/lists-and-charts/albums-of-{year}")
    public String getAllAlbumsByYear(@PathVariable String year, Model model) {
        List<Album> allAlbumsByYear = albumService.getAllAlbumsByYear(Integer.valueOf(year));
        List<AlbumViewDTO> sortedAlbumViewDTOList = albumService.getSortedAlbumViewDTOList(allAlbumsByYear, SortType.RATING);
        model.addAttribute(ALBUM_LIST, sortedAlbumViewDTOList);
        model.addAttribute(YEAR, year);
        return "music/albums-of-year";
    }

    @GetMapping("/lists-and-charts/albums-of-{decade}s")
    public String getAllAlbumsByDecade(@PathVariable String decade, Model model) {
        List<Album> allAlbumByDecade = albumService.getAllAlbumByDecade(decade);
        List<AlbumViewDTO> albumViewDTOList = albumService.getAlbumViewDTOList(allAlbumByDecade);
        model.addAttribute(ALBUM_LIST, albumViewDTOList);
        if (AlbumService.DECADE_2010s.equals(decade)) {
            model.addAttribute(DECADE, "2010-x");
        } else if (AlbumService.DECADE_2020s.equals(decade)) {
            model.addAttribute(DECADE, "2020-x");
        }
        return "music/albums-of-decade";
    }

    @GetMapping("/period/{periodSlug}")
    public String getPeriodBySlug(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getMusicPeriodBySlug(periodSlug);
        MusicPeriodViewDTO musicPeriodViewDTO = musicPeriodService.getMusicPeriodViewDTO(musicPeriod);
        List<Musician> allMusiciansByPeriod = musicianService.getAllMusiciansByPeriod(musicPeriod);
        List<Musician> bestMusiciansByPeriod = musicianService.getTop10MusiciansByPeriod(allMusiciansByPeriod);
        List<CompositionViewDTO> allCompositionsByPeriod = compositionService
                .getSortedCompositionViewDTOList(compositionService
                        .getAllByMusicianList(allMusiciansByPeriod), SortType.RATING);
        model.addAttribute(MUSIC_PERIOD, musicPeriodViewDTO);
        model.addAttribute(MUSICIAN_LIST, bestMusiciansByPeriod);
        model.addAttribute(COMPOSITION_LIST, allCompositionsByPeriod);
        return "music/music-period";
    }

    @GetMapping("/genre/{genreSlug}")
    public String getGenreBySlug(@PathVariable String genreSlug, Model model) {
        MusicGenre musicGenre = musicGenreService.getMusicGenreBySlug(genreSlug);
        model.addAttribute(MUSIC_GENRE, musicGenre);
        model.addAttribute(CLASSICAL_TYPE, MusicGenreType.CLASSICAL);
        model.addAttribute(CONTEMPORARY_TYPE, MusicGenreType.CONTEMPORARY);
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY)) {
            List<Album> albumsByGenre = albumService.getAllAlbumsByGenre(musicGenre);
            model.addAttribute(ALBUM_LIST, albumService
                    .getSortedAlbumViewDTOList(albumsByGenre, SortType.RATING));
        }
        if (musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL)) {
            List<Composition> allCompositionsByGenre = compositionService.getAllByGenre(musicGenre);
            model.addAttribute(COMPOSITION_LIST, compositionService
                    .getSortedCompositionViewDTOList(allCompositionsByGenre, SortType.RATING));
        }
        return "music/music-genre";
    }
}
