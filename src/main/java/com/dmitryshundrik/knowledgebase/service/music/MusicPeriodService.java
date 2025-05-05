package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;

import java.util.List;

public interface MusicPeriodService {

    MusicPeriod getBySlug(String musicPeriodSlug);

    List<MusicPeriod> getAllOrderByStart();

    String createMusicPeriod(MusicPeriodCreateEditDto periodDto);
    String updateMusicPeriod(String periodSlug, MusicPeriodCreateEditDto periodDto);
    void deleteMusicPeriod(MusicPeriod period);

    MusicPeriodViewDto getMusicPeriodViewDto(MusicPeriod musicPeriod);
    List<MusicPeriodViewDto> getMusicPeriodViewDtoList(List<MusicPeriod> musicPeriodList);
    MusicPeriodCreateEditDto getMusicPeriodCreateEditDto(MusicPeriod musicPeriod);

    String isSlugExists(String musicPeriodSlug);
}
