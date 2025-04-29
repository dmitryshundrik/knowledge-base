package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.core.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EntityEventService {

    private final MusicianService musicianService;

    private final WriterService writerService;

    private final ArtistService artistService;

    private final EntityEvenCreator entityEvenCreator;

    public List<CurrentEventInfo> getCurrentEvents(Integer dayInterval) {
        List<CurrentEventInfo> currentEventInfoList = new ArrayList<>();
        currentEventInfoList.addAll(getMusicianEvents(dayInterval));
        currentEventInfoList.addAll(getWriterEvents(dayInterval));
        currentEventInfoList.addAll(getArtistEvents(dayInterval));
        return currentEventInfoList.stream()
                .sorted(Comparator.comparing(CurrentEventInfo::getMonth).thenComparing(CurrentEventInfo::getDay))
                .collect(Collectors.toList());
    }

    public List<CurrentEventInfo> getMusicianEvents(Integer dayInterval) {
        Set<Musician> musicianBirthList = musicianService.getAllWithCurrentBirth(dayInterval);
        Set<Musician> musicianDeathList = musicianService.getAllWithCurrentDeath(dayInterval);
        return entityEvenCreator.createMusicianEvents(musicianBirthList, musicianDeathList);
    }

    public List<CurrentEventInfo> getWriterEvents(Integer dayInterval) {
        Set<Writer> writerBirthList = writerService.getAllWithCurrentBirth(dayInterval);
        Set<Writer> writerDeathList = writerService.getAllWithCurrentDeath(dayInterval);
        return entityEvenCreator.createWriterEvents(writerBirthList, writerDeathList);
    }

    public List<CurrentEventInfo> getArtistEvents(Integer dayInterval) {
        Set<Artist> artistBirthList = artistService.getAllWithCurrentBirth(dayInterval);
        Set<Artist> artistDeathList = artistService.getAllWithCurrentDeath(dayInterval);
        return entityEvenCreator.createArtistEvents(artistBirthList, artistDeathList);
    }
}
