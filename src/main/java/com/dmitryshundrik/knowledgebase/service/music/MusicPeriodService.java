package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodCreateEditDTO;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.music.MusicPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicPeriodService {

    @Autowired
    private MusicPeriodRepository musicPeriodRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    public List<MusicPeriod> getAll() {
        return musicPeriodRepository.findAll();
    }

    public MusicPeriod getMusicPeriodBySlug(String slug) {
        return musicPeriodRepository.getBySlug(slug);
    }

    public List<MusicPeriod> getFilteredMusicPeriods() {
        return musicPeriodRepository.findAll().stream()
                .filter(period -> {
                    if (!compositionRepository.getAllByMusicPeriodsIsContaining(period).isEmpty()) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
    }

    public String createMusicPeriodByDTO(MusicPeriodCreateEditDTO periodDTO) {
        MusicPeriod period = new MusicPeriod();
        period.setCreated(Instant.now());
        setFieldsFromDTO(period, periodDTO);
        musicPeriodRepository.save(period);
        return period.getSlug();
    }

    public String updateMusicPeriod(String periodSlug, MusicPeriodCreateEditDTO periodDTO) {
        MusicPeriod period = getMusicPeriodBySlug(periodSlug);
        setFieldsFromDTO(period, periodDTO);
        return periodDTO.getSlug();
    }

    public void deleteMusicPeriod(MusicPeriod period) {
        musicPeriodRepository.delete(period);
    }

    public MusicPeriodCreateEditDTO getMusicPeriodCreateEditDTO(MusicPeriod musicPeriod) {
        return MusicPeriodCreateEditDTO.builder()
                .slug(musicPeriod.getSlug())
                .title(musicPeriod.getTitle())
                .titleEn(musicPeriod.getTitleEn())
                .approximateStart(musicPeriod.getApproximateStart())
                .approximateEnd(musicPeriod.getApproximateEnd())
                .description(musicPeriod.getDescription())
                .build();
    }

    private void setFieldsFromDTO(MusicPeriod period, MusicPeriodCreateEditDTO periodDTO) {
        period.setSlug(periodDTO.getSlug());
        period.setTitle(periodDTO.getTitle());
        period.setTitleEn(periodDTO.getTitleEn());
        period.setApproximateStart(periodDTO.getApproximateStart());
        period.setApproximateEnd(periodDTO.getApproximateEnd());
        period.setDescription(periodDTO.getDescription());
    }
}
