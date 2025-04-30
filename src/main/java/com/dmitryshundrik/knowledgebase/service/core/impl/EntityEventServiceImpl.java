package com.dmitryshundrik.knowledgebase.service.core.impl;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentEvent;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.service.core.EntityEventCreator;
import com.dmitryshundrik.knowledgebase.service.core.EntityEventService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.ENTITY_EVENT_CACHE;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntityEventServiceImpl implements EntityEventService {

    private final MusicianService musicianService;

    private final WriterService writerService;

    private final ArtistService artistService;

    private final EntityEventCreator entityEventCreator;

    @Override
    @Cacheable(value = ENTITY_EVENT_CACHE, key = "#root.methodName")
    public List<EntityCurrentEvent> getEntityEvents(Integer dayInterval) {
        return processEntityEvents(dayInterval);
    }

    @Override
    @CachePut(value = ENTITY_EVENT_CACHE, key = "'getCurrentEvents'")
    public List<EntityCurrentEvent> processEntityEvents(Integer dayInterval) {
        List<EntityCurrentEvent> entityCurrentEventList = new ArrayList<>();
        entityCurrentEventList.addAll(getMusicianEvents(dayInterval));
        entityCurrentEventList.addAll(getWriterEvents(dayInterval));
        entityCurrentEventList.addAll(getArtistEvents(dayInterval));
        return entityCurrentEventList.stream()
                .sorted(Comparator.comparing(EntityCurrentEvent::getMonth).thenComparing(EntityCurrentEvent::getDay))
                .collect(Collectors.toList());
    }

    private List<EntityCurrentEvent> getMusicianEvents(Integer dayInterval) {
        Set<Musician> musicianBirthList = musicianService.getAllWithCurrentBirth(dayInterval);
        Set<Musician> musicianDeathList = musicianService.getAllWithCurrentDeath(dayInterval);
        return entityEventCreator.createMusicianEvents(musicianBirthList, musicianDeathList);
    }

    private List<EntityCurrentEvent> getWriterEvents(Integer dayInterval) {
        Set<Writer> writerBirthList = writerService.getAllWithCurrentBirth(dayInterval);
        Set<Writer> writerDeathList = writerService.getAllWithCurrentDeath(dayInterval);
        return entityEventCreator.createWriterEvents(writerBirthList, writerDeathList);
    }

    private List<EntityCurrentEvent> getArtistEvents(Integer dayInterval) {
        Set<Artist> artistBirthList = artistService.getAllWithCurrentBirth(dayInterval);
        Set<Artist> artistDeathList = artistService.getAllWithCurrentDeath(dayInterval);
        return entityEventCreator.createArtistEvents(artistBirthList, artistDeathList);
    }
}
