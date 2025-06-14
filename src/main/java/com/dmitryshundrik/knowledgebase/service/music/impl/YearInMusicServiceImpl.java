package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicViewDto;
import com.dmitryshundrik.knowledgebase.repository.music.YearInMusicRepository;
import com.dmitryshundrik.knowledgebase.service.music.YearInMusicService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.YEAR_IN_MUSIC_NOT_FOUND_MESSAGE;

@Service
@Transactional
@RequiredArgsConstructor
public class YearInMusicServiceImpl implements YearInMusicService {

    private final YearInMusicRepository yearInMusicRepository;

    private final MusicianServiceImpl musicianService;

    public YearInMusic getBySlug(String yearInMusicSlug) {
        return yearInMusicRepository.findBySlug(yearInMusicSlug)
                .orElseThrow(() -> new NotFoundException(YEAR_IN_MUSIC_NOT_FOUND_MESSAGE.formatted(yearInMusicSlug)));
    }

    public YearInMusic getByYear(Integer year) {
        return yearInMusicRepository.findByYear(year)
                .orElseThrow(() -> new NotFoundException(YEAR_IN_MUSIC_NOT_FOUND_MESSAGE.formatted(year)));
    }

    public List<YearInMusic> getAll() {
        return yearInMusicRepository.findAllByOrderByYearAsc();
    }

    public YearInMusic createYearInMusic(YearInMusicCreateEditDto yearInMusicDto) {
        YearInMusic yearInMusic = new YearInMusic();
        setFieldsFromDto(yearInMusic, yearInMusicDto);
        return yearInMusicRepository.save(yearInMusic);
    }

    public YearInMusic updateYearInMusic(YearInMusic yearInMusic, YearInMusicCreateEditDto yearInMusicDto) {
        setFieldsFromDto(yearInMusic, yearInMusicDto);
        return yearInMusic;
    }

    public void deleteYearInMusic(YearInMusic yearInMusic) {
        yearInMusicRepository.delete(yearInMusic);
    }

    public YearInMusicViewDto getYearInMusicViewDto(YearInMusic yearInMusic) {
        return YearInMusicViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(yearInMusic.getCreated()))
                .slug(yearInMusic.getSlug())
                .title(yearInMusic.getTitle())
                .year(yearInMusic.getYear())
                .bestMaleSinger(yearInMusic.getBestMaleSinger() != null ? yearInMusic.getBestMaleSinger().getNickName() : null)
                .bestMaleSingerSlug(yearInMusic.getBestMaleSinger() != null ? yearInMusic.getBestMaleSinger().getSlug() : null)
                .bestFemaleSinger(yearInMusic.getBestFemaleSinger() != null ? yearInMusic.getBestFemaleSinger().getNickName() : null)
                .bestFemaleSingerSlug(yearInMusic.getBestFemaleSinger() != null ? yearInMusic.getBestFemaleSinger().getSlug() : null)
                .bestGroup(yearInMusic.getBestGroup() != null ? yearInMusic.getBestGroup().getNickName() : null)
                .bestGroupSlug(yearInMusic.getBestGroup() != null ? yearInMusic.getBestGroup().getSlug() : null)
                .bestComposer(yearInMusic.getBestComposer() != null ? yearInMusic.getBestComposer().getNickName() : null)
                .bestComposerSlug(yearInMusic.getBestComposer() != null ? yearInMusic.getBestComposer().getSlug() : null)
                .aotyListDescription(yearInMusic.getAotyListDescription())
                .aotySpotifyLink(yearInMusic.getAotySpotifyLink())
                .sotyListDescription(yearInMusic.getSotyListDescription())
                .sotySpotifyLink(yearInMusic.getSotySpotifyLink())
                .build();

    }

    public List<YearInMusicViewDto> getYearInMusicViewDtoList(List<YearInMusic> yearInMusicList) {
        return yearInMusicList.stream()
                .map(this::getYearInMusicViewDto)
                .collect(Collectors.toList());
    }

    public List<YearInMusicSimpleDto> getYearInMusicSimpleDtoList() {
        return yearInMusicRepository.findAllOrderByYearAcs();
    }

    public YearInMusicCreateEditDto getYearInMusicCreateEditDto(YearInMusic yearInMusic) {
        return YearInMusicCreateEditDto.builder()
                .slug(yearInMusic.getSlug())
                .title(yearInMusic.getTitle())
                .year(yearInMusic.getYear())
                .bestMaleSingerId(yearInMusic.getBestMaleSinger() != null ? yearInMusic.getBestMaleSinger().getId().toString() : null)
                .bestFemaleSingerId(yearInMusic.getBestFemaleSinger() != null ? yearInMusic.getBestFemaleSinger().getId().toString() : null)
                .bestGroupId(yearInMusic.getBestGroup() != null ? yearInMusic.getBestGroup().getId().toString() : null)
                .bestComposerId(yearInMusic.getBestComposer() != null ? yearInMusic.getBestComposer().getId().toString() : null)
                .aotyListDescription(yearInMusic.getAotyListDescription())
                .aotySpotifyLink(yearInMusic.getAotySpotifyLink())
                .sotyListDescription(yearInMusic.getSotyListDescription())
                .sotySpotifyLink(yearInMusic.getSotySpotifyLink())
                .build();
    }

    private void setFieldsFromDto(YearInMusic yearInMusic, YearInMusicCreateEditDto yearInMusicDto) {
        yearInMusic.setSlug(yearInMusicDto.getSlug().trim());
        yearInMusic.setTitle(yearInMusicDto.getTitle().trim());
        yearInMusic.setYear(yearInMusicDto.getYear());
        yearInMusic.setBestMaleSinger(!StringUtils.isBlank(yearInMusicDto.getBestMaleSingerId()) ? musicianService
                .getById(UUID.fromString(yearInMusicDto.getBestMaleSingerId())) : null);
        yearInMusic.setBestFemaleSinger(!StringUtils.isBlank(yearInMusicDto.getBestFemaleSingerId()) ? musicianService
                .getById(UUID.fromString(yearInMusicDto.getBestFemaleSingerId())) : null);
        yearInMusic.setBestGroup(!StringUtils.isBlank(yearInMusicDto.getBestGroupId()) ? musicianService
                .getById(UUID.fromString(yearInMusicDto.getBestGroupId())) : null);
        yearInMusic.setBestComposer(!StringUtils.isBlank(yearInMusicDto.getBestComposerId()) ? musicianService
                .getById(UUID.fromString(yearInMusicDto.getBestComposerId())) : null);
        yearInMusic.setAotyListDescription(yearInMusicDto.getAotyListDescription());
        yearInMusic.setAotySpotifyLink(yearInMusicDto.getAotySpotifyLink());
        yearInMusic.setSotyListDescription(yearInMusicDto.getSotyListDescription());
        yearInMusic.setSotySpotifyLink(yearInMusicDto.getSotySpotifyLink());
    }
}
