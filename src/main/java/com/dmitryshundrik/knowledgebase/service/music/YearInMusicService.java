package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.YearInMusicViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.YearInMusicRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class YearInMusicService {

    @Autowired
    private YearInMusicRepository yearInMusicRepository;

    @Autowired
    private MusicianService musicianService;

    public YearInMusic getYearInMusicBySlug(String yearInMusicSlug) {
        return yearInMusicRepository.findBySlug(yearInMusicSlug);
    }

    public YearInMusic getYearInMusicByYear(Integer year) {
        return yearInMusicRepository.findByYear(year);
    }

    public List<YearInMusic> getAll() {
        return yearInMusicRepository.findAll().stream()
                .sorted(Comparator.comparing(YearInMusic::getYear)).collect(Collectors.toList());
    }

    public YearInMusicViewDTO createYearInMusic(YearInMusicCreateEditDTO yearInMusicDTO) {
        YearInMusic yearInMusic = new YearInMusic();
        yearInMusic.setCreated(Instant.now());
        setFieldsFromDTO(yearInMusic, yearInMusicDTO);
        YearInMusic createdYearInMusic = yearInMusicRepository.save(yearInMusic);
        return getYearInMusicViewDTO(createdYearInMusic);
    }

    public YearInMusicViewDTO updateYearInMusic(YearInMusic yearInMusic, YearInMusicCreateEditDTO yearInMusicDTO) {
        setFieldsFromDTO(yearInMusic, yearInMusicDTO);
        return getYearInMusicViewDTO(yearInMusic);
    }

    public void deleteYearInMusic(YearInMusic yearInMusic) {
        yearInMusicRepository.delete(yearInMusic);
    }

    public YearInMusicViewDTO getYearInMusicViewDTO(YearInMusic yearInMusic) {
        return YearInMusicViewDTO.builder()
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

    public List<YearInMusicViewDTO> getYearInMusicViewDTOList(List<YearInMusic> yearInMusicList) {
        return yearInMusicList.stream()
                .map(this::getYearInMusicViewDTO)
                .collect(Collectors.toList());
    }

    public List<YearInMusicViewDTO> getSortedYearInMusicViewDTOList() {
        return getYearInMusicViewDTOList(getAll()).stream()
                .sorted(Comparator.comparing(YearInMusicViewDTO::getYear))
                .collect(Collectors.toList());
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
        yearInMusic.setSlug(yearInMusicDTO.getSlug().trim());
        yearInMusic.setTitle(yearInMusicDTO.getTitle().trim());
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
