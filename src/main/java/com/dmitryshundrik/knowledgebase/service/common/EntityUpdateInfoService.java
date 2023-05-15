package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.EntityUpdateInfo;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EntityUpdateInfoService {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    public List<EntityUpdateInfo> getAll() {
        List<EntityUpdateInfo> allUpdateInfos = new ArrayList<>();

        allUpdateInfos.addAll(getMusicianUpdates());
        allUpdateInfos.addAll(getAlbumUpdates());
        allUpdateInfos.addAll(getCompositionUpdates());

        return allUpdateInfos.stream()
                .sorted((o1, o2) -> o2.getCreated().compareTo(o1.getCreated()))
                .limit(20)
                .collect(Collectors.toList());
    }

    private List<EntityUpdateInfo> getMusicianUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<Musician> latestUpdate = musicianService.getLatestUpdate();
        for (Musician musician : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(musician.getCreated())
                    .archiveSection("музыка:")
                    .description("добавилен музыкант " + musician.getNickName())
                    .link("music/musician/" + musician.getSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }

    private List<EntityUpdateInfo> getAlbumUpdates() {
        List<EntityUpdateInfo> albumUpdateInfo = new ArrayList<>();
        List<Album> latestUpdate = albumService.getLatestUpdate();
        for (Album album : latestUpdate) {
            albumUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(album.getCreated())
                    .archiveSection("музыка:")
                    .description("альбом " + album.getTitle() + " добавлен в архив " + album.getMusician().getNickName())
                    .link("music/musician/" + album.getMusician().getSlug())
                    .build());
        }
        return albumUpdateInfo;
    }

    private List<EntityUpdateInfo> getCompositionUpdates() {
        List<EntityUpdateInfo> compositionUpdateInfo = new ArrayList<>();
        List<Composition> latestUpdate = compositionService.getLatestUpdate();
        for (Composition composition : latestUpdate) {
            compositionUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(composition.getCreated())
                    .archiveSection("музыка:")
                    .description("композиция " + composition.getTitle() + " добавлена в архив " + composition.getMusician().getNickName())
                    .link("music/musician/" + composition.getMusician().getSlug())
                    .build());
        }
        return compositionUpdateInfo;
    }
}
