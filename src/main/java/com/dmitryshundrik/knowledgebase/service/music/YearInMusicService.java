package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicCreateEditDTO;
import com.dmitryshundrik.knowledgebase.repository.music.YearInMusicRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class YearInMusicService {

    @Autowired
    private YearInMusicRepository yearInMusicRepository;

    @Autowired
    private MusicianService musicianService;

    public YearInMusic getYearInMusicBySlug(String slug) {
        return yearInMusicRepository.findBySlug(slug);
    }

    public List<YearInMusic> getAll() {
        return yearInMusicRepository.findAll();
    }

    public YearInMusicCreateEditDTO createYearInMusic(YearInMusicCreateEditDTO yearInMusicDTO) {
        YearInMusic yearInMusic = new YearInMusic();
        yearInMusic.setCreated(Instant.now());
        setFieldsFromDTO(yearInMusic, yearInMusicDTO);
        YearInMusic createdYearInMusic = yearInMusicRepository.save(yearInMusic);
        return getYearInMusicCreateEditDTO(createdYearInMusic);
    }

    public void updateYearInMusic(YearInMusic yearInMusic, YearInMusicCreateEditDTO yearInMusicDTO) {
        setFieldsFromDTO(yearInMusic, yearInMusicDTO);
    }

    public void deleteYearInMusic(YearInMusic yearInMusic) {
        yearInMusicRepository.delete(yearInMusic);
    }

    public YearInMusicCreateEditDTO getYearInMusicCreateEditDTO(YearInMusic yearInMusic) {
        return YearInMusicCreateEditDTO.builder()
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

    public void setFieldsFromDTO(YearInMusic yearInMusic, YearInMusicCreateEditDTO yearInMusicDTO) {
        yearInMusic.setSlug(yearInMusicDTO.getSlug());
        yearInMusic.setTitle(yearInMusicDTO.getTitle());
        yearInMusic.setYear(yearInMusicDTO.getYear());
        yearInMusic.setBestMaleSinger(!StringUtils.isBlank(yearInMusicDTO.getBestMaleSingerId()) ? musicianService
                .getMusicianById(UUID.fromString(yearInMusicDTO.getBestMaleSingerId())) : null);
        yearInMusic.setBestFemaleSinger(!StringUtils.isBlank(yearInMusicDTO.getBestFemaleSingerId()) ? musicianService
                .getMusicianById(UUID.fromString(yearInMusicDTO.getBestFemaleSingerId())) : null);
        yearInMusic.setBestGroup(!StringUtils.isBlank(yearInMusicDTO.getBestGroupId()) ? musicianService
                .getMusicianById(UUID.fromString(yearInMusicDTO.getBestGroupId())) : null);
        yearInMusic.setBestComposer(!StringUtils.isBlank(yearInMusicDTO.getBestComposerId()) ? musicianService
                .getMusicianById(UUID.fromString(yearInMusicDTO.getBestComposerId())) : null);
        yearInMusic.setAotyListDescription(yearInMusicDTO.getAotyListDescription());
        yearInMusic.setAotySpotifyLink(yearInMusicDTO.getAotySpotifyLink());
        yearInMusic.setSotyListDescription(yearInMusicDTO.getSotyListDescription());
        yearInMusic.setSotySpotifyLink(yearInMusicDTO.getSotySpotifyLink());
    }
}
