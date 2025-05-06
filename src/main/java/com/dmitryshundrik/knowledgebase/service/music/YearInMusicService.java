package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import java.util.List;

public interface YearInMusicService {

    YearInMusic getBySlug(String yearInMusicSlug);
    YearInMusic getByYear(Integer year);

    List<YearInMusic> getAll();

    YearInMusic createYearInMusic(YearInMusicCreateEditDto yearInMusicDto);
    YearInMusic updateYearInMusic(YearInMusic yearInMusic, YearInMusicCreateEditDto yearInMusicDto);
    void deleteYearInMusic(YearInMusic yearInMusic);

    YearInMusicViewDto getYearInMusicViewDto(YearInMusic yearInMusic);
    List<YearInMusicViewDto> getYearInMusicViewDtoList(List<YearInMusic> yearInMusicList);
    List<YearInMusicSimpleDto> getYearInMusicSimpleDtoList();
    YearInMusicCreateEditDto getYearInMusicCreateEditDto(YearInMusic yearInMusic);

}
