package com.dmitryshundrik.knowledgebase.controller.music;

import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbums;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtists;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.service.client.LastfmService;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.EraType;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianViewDto;
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
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2010s;
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2020s;
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

    private final LastfmService lastFmService;

    @GetMapping
    public String getMusicPage(Model model) {
        model.addAttribute(YEAR_IN_MUSIC, yearInMusicService.getYearInMusicSimpleDtoList());
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllOrderByStart());
        model.addAttribute(ALBUM_RATINGS, albumService.getAllYearsFromAlbums());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getFilteredClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getFilteredContemporaryGenres());
        return "music/music-page";
    }

    @GetMapping("/lists-and-charts/{slug}")
    public String getYearInMusic(@PathVariable String slug, Model model) {
        YearInMusic yearInMusic = yearInMusicService.getBySlug(slug);

        Integer previousYear = yearInMusic.getYear() - 1;
        if (yearInMusicService.existsByYear(previousYear)) {
            model.addAttribute(PREVIOUS_YEAR, yearInMusicService
                    .getYearInMusicViewDto(yearInMusicService.getByYear(previousYear)));
        }
        Integer nextYear = yearInMusic.getYear() + 1;
        if (yearInMusicService.existsByYear(nextYear)) {
            model.addAttribute(NEXT_YEAR, yearInMusicService
                    .getYearInMusicViewDto(yearInMusicService.getByYear(nextYear)));
        }
        model.addAttribute(CURRENT_YEAR, yearInMusicService.getYearInMusicViewDto(yearInMusic));
        model.addAttribute(ALBUM_LIST, albumService.get10BestAlbumsByYear(yearInMusic.getYear()));
        model.addAttribute(COMPOSITION_LIST, compositionService.getCompositionViewDtoListForSoty(yearInMusic.getYear()));
        return "music/year-in-music";
    }

    @GetMapping("/album/top100")
    public String getTop100BestAlbums(Model model) {
        List<AlbumSimpleDto> albumDtoList = albumService.getTop100BestAlbumSimpleDto();
        model.addAttribute(ALBUM_LIST, albumDtoList);
        return "music/album-top100";
    }

    @GetMapping("/composition/top100")
    public String getTop100BestClassicalCompositions(Model model) {
        List<CompositionSimpleDto> compositionDtoList = compositionService.getTop100BestCompositionSimpleDto();
        model.addAttribute(COMPOSITION_LIST, compositionDtoList);
        return "music/classical-composition-top100";
    }

    @GetMapping("/album/all")
    public String getAllAlbums(Model model) {
        model.addAttribute(ALBUM_LIST, albumService.getAllAlbumSimpleDto());
        return "music/album-all";
    }

    @GetMapping("/musician/all")
    public String getAllMusicians(Model model) {
        List<MusicianSimpleDto> musicianDtoList = musicianService.getAllMusicianSimpleDto();
        model.addAttribute(MUSICIAN_LIST, musicianDtoList);
        return "music/musician-all";
    }

    @GetMapping("/musician/{slug}")
    public String getMusicianBySlug(@PathVariable String slug, Model model) {
        Musician musician = musicianService.getBySlug(slug);
        MusicianViewDto musicianDto = musicianService.getMusicianViewDto(musician);
        model.addAttribute(MUSICIAN, musicianDto);
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
        List<Resource> resourceList = resourcesService.getAllByResourceType(ResourceType.MUSIC);
        model.addAttribute(RESOURCE_LIST, resourceList);
        return "music/music-resources";
    }

    @GetMapping("/listening-stats")
    public String getListeningStats(Model model) {
        TopArtists topArtists = lastFmService.getTopArtists();
        TopAlbums topAlbums = lastFmService.getTopAlbums();
        model.addAttribute("artistList", topArtists.getArtists());
        model.addAttribute("albumsList", topAlbums.getAlbums());
        return "music/listening-stats";
    }

    @GetMapping("/lists-and-charts/albums-of-{year}")
    public String getAllAlbumsByYear(@PathVariable String year, Model model) {
        List<AlbumSimpleDto> albumDtoList = albumService.getAllAlbumSimpleDtoByYear(Integer.valueOf(year));
        model.addAttribute(ALBUM_LIST, albumDtoList);
        model.addAttribute(YEAR, year);
        return "music/albums-of-year";
    }

    @GetMapping("/lists-and-charts/albums-of-{decade}s")
    public String getAllAlbumsByDecade(@PathVariable String decade, Model model) {
        List<AlbumSimpleDto> albumDtoList = albumService.getAllAlbumSimpleDtoByDecade(decade);
        model.addAttribute(ALBUM_LIST, albumDtoList);
        if (DECADE_2010s.equals(decade)) {
            model.addAttribute(DECADE, "2010-x");
        } else if (DECADE_2020s.equals(decade)) {
            model.addAttribute(DECADE, "2020-x");
        }
        return "music/albums-of-decade";
    }

    @GetMapping("/period/{periodSlug}")
    public String getPeriodBySlug(@PathVariable String periodSlug, Model model) {
        MusicPeriod musicPeriod = musicPeriodService.getBySlug(periodSlug);
        MusicPeriodViewDto musicPeriodDto = musicPeriodService.getMusicPeriodViewDto(musicPeriod);
        List<Musician> musicianList = musicianService.getAllByPeriod(musicPeriod);
        model.addAttribute(MUSIC_PERIOD, musicPeriodDto);
        model.addAttribute(MUSICIAN_LIST, musicianService.getAllBestByPeriod(musicPeriod));
        model.addAttribute(COMPOSITION_LIST, compositionService.getCompositionViewDtoListOrderBy(compositionService
                .getAllByMusicianList(musicianList), SortType.RATING));
        return "music/music-period";
    }

    @GetMapping("/genre/{genreSlug}")
    public String getGenreBySlug(@PathVariable String genreSlug, Model model) {
        MusicGenre musicGenre = musicGenreService.getBySlug(genreSlug);
        model.addAttribute(MUSIC_GENRE, musicGenre);
        model.addAttribute(CLASSICAL_TYPE, MusicGenreType.CLASSICAL);
        model.addAttribute(CONTEMPORARY_TYPE, MusicGenreType.CONTEMPORARY);
        if (MusicGenreType.CONTEMPORARY.equals(musicGenre.getMusicGenreType())) {
            List<Album> albumsByGenre = albumService.getAllByGenre(musicGenre);
            model.addAttribute(ALBUM_LIST, albumService
                    .getAlbumViewDtoListOrderBy(albumsByGenre, SortType.RATING));
        } else if (MusicGenreType.CLASSICAL.equals(musicGenre.getMusicGenreType())) {
            List<Composition> compositionsByGenre = compositionService.getAllByGenre(musicGenre);
            model.addAttribute(COMPOSITION_LIST, compositionService
                    .getCompositionViewDtoListOrderBy(compositionsByGenre, SortType.RATING));
        }
        return "music/music-genre";
    }
}
