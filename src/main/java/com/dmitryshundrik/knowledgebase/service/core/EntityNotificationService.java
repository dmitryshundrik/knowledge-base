package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.core.CurrentEventInfo;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntityNotificationService {

    private final MusicianService musicianService;

    private final WriterService writerService;

    private final ArtistService artistService;

    private final EntityEvenCreator entityEvenCreator;

    public List<CurrentEventInfo> getCurrentNotifications(Integer dayInterval) {
        List<CurrentEventInfo> currentEventInfoList = new ArrayList<>();
        currentEventInfoList.addAll(getMusicianNotifications(dayInterval));
        currentEventInfoList.addAll(getWriterNotifications(dayInterval));
        currentEventInfoList.addAll(getArtistNotifications(dayInterval));
        return currentEventInfoList.stream()
                .sorted(Comparator.comparing(CurrentEventInfo::getMonth).thenComparing(CurrentEventInfo::getDay))
                .collect(Collectors.toList());
    }

    public List<CurrentEventInfo> getMusicianNotifications(Integer dayInterval) {
        Set<Musician> musicianBirthList = musicianService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Musician> musicianDeathList = musicianService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEvenCreator.createMusicianEvents(musicianBirthList, musicianDeathList);
    }

    public List<CurrentEventInfo> getWriterNotifications(Integer dayInterval) {
        Set<Writer> entityBirthList = writerService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Writer> entityDeathList = writerService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEvenCreator.createWriterEvents(entityBirthList, entityDeathList);
    }

    public List<CurrentEventInfo> getArtistNotifications(Integer dayInterval) {
        Set<Artist> entityBirthList = artistService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Artist> entityDeathList = artistService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEvenCreator.createArtistEvents(entityBirthList, entityDeathList);
    }
}
