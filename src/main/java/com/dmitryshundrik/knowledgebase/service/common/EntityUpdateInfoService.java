package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.EntityUpdateInfo;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianViewDTO;
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
                .limit(10)
                .collect(Collectors.toList());
    }

    private List<EntityUpdateInfo> getMusicianUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<MusicianViewDTO> latestUpdate = musicianService.getLatestUpdate();
        for (MusicianViewDTO musician : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(musician.getCreated().split(" ")[0])
                    .archiveSection("музыка:")
                    .description("добавлен музыкант " + musician.getNickName())
                    .link("music/musician/" + musician.getSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }

    private List<EntityUpdateInfo> getAlbumUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<AlbumViewDTO> latestUpdate = albumService.getLatestUpdate();
        for (AlbumViewDTO album : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(album.getCreated().split(" ")[0])
                    .archiveSection("музыка:")
                    .description("добавлен альбом " + album.getTitle() + " в архив " + album.getMusicianNickname())
                    .link("music/musician/" + album.getMusicianSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }

    private List<EntityUpdateInfo> getCompositionUpdates() {
        List<EntityUpdateInfo> musicianUpdateInfo = new ArrayList<>();
        List<CompositionViewDTO> latestUpdate = compositionService.getLatestUpdate();
        for (CompositionViewDTO composition : latestUpdate) {
            musicianUpdateInfo.add(EntityUpdateInfo.builder()
                    .created(composition.getCreated().split(" ")[0])
                    .archiveSection("музыка:")
                    .description("добавлена композиция " + composition.getTitle() + " в архив " + composition.getMusicianNickname())
                    .link("music/musician/" + composition.getMusicianSlug())
                    .build());
        }
        return musicianUpdateInfo;
    }
}