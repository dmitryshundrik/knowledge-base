package com.dmitryshundrik.knowledgebase.service.core.impl;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentEvent;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.service.core.EntityEventCreator;
import com.dmitryshundrik.knowledgebase.service.core.EntityNotificationService;
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
public class EntityNotificationServiceImpl implements EntityNotificationService {

    private final MusicianService musicianService;

    private final WriterService writerService;

    private final ArtistService artistService;

    private final EntityEventCreator entityEventCreator;

    @Override
    public List<EntityCurrentEvent> getCurrentNotifications(Integer dayInterval) {
        List<EntityCurrentEvent> entityCurrentEventList = new ArrayList<>();
        entityCurrentEventList.addAll(getMusicianNotifications(dayInterval));
        entityCurrentEventList.addAll(getWriterNotifications(dayInterval));
        entityCurrentEventList.addAll(getArtistNotifications(dayInterval));
        return entityCurrentEventList.stream()
                .sorted(Comparator.comparing(EntityCurrentEvent::getMonth).thenComparing(EntityCurrentEvent::getDay))
                .collect(Collectors.toList());
    }

    private List<EntityCurrentEvent> getMusicianNotifications(Integer dayInterval) {
        Set<Musician> musicianBirthList = musicianService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Musician> musicianDeathList = musicianService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEventCreator.createMusicianEvents(musicianBirthList, musicianDeathList);
    }

    private List<EntityCurrentEvent> getWriterNotifications(Integer dayInterval) {
        Set<Writer> entityBirthList = writerService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Writer> entityDeathList = writerService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEventCreator.createWriterEvents(entityBirthList, entityDeathList);
    }

    private List<EntityCurrentEvent> getArtistNotifications(Integer dayInterval) {
        Set<Artist> entityBirthList = artistService.getAllWithCurrentBirthAndNotification(dayInterval);
        Set<Artist> entityDeathList = artistService.getAllWithCurrentDeathAndNotification(dayInterval);
        return entityEventCreator.createArtistEvents(entityBirthList, entityDeathList);
    }
}
