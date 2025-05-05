package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDto;
import com.dmitryshundrik.knowledgebase.repository.music.MusicPeriodRepository;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicPeriodServiceImpl implements MusicPeriodService {

    private final MusicPeriodRepository musicPeriodRepository;

    @Override
    public MusicPeriod getBySlug(String musicPeriodSlug) {
        return musicPeriodRepository.findBySlug(musicPeriodSlug);
    }

    @Override
    public List<MusicPeriod> getAllOrderByStart() {
        return musicPeriodRepository.findAllByOrderByApproximateStartAsc();
    }

    @Override
    public String createMusicPeriod(MusicPeriodCreateEditDto periodDto) {
        MusicPeriod period = new MusicPeriod();
        setFieldsFromDto(period, periodDto);
        musicPeriodRepository.save(period);
        return period.getSlug();
    }

    @Override
    public String updateMusicPeriod(String periodSlug, MusicPeriodCreateEditDto periodDto) {
        MusicPeriod period = getBySlug(periodSlug);
        setFieldsFromDto(period, periodDto);
        return periodDto.getSlug();
    }

    @Override
    public void deleteMusicPeriod(MusicPeriod period) {
        musicPeriodRepository.delete(period);
    }

    @Override
    public MusicPeriodViewDto getMusicPeriodViewDto(MusicPeriod musicPeriod) {
        return MusicPeriodViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(musicPeriod.getCreated()))
                .slug(musicPeriod.getSlug())
                .title(musicPeriod.getTitle())
                .titleEn(musicPeriod.getTitleEn())
                .approximateStart(musicPeriod.getApproximateStart())
                .approximateEnd(musicPeriod.getApproximateEnd())
                .description(musicPeriod.getDescription())
                .build();
    }

    @Override
    public List<MusicPeriodViewDto> getMusicPeriodViewDtoList(List<MusicPeriod> musicPeriodList) {
        return musicPeriodList.stream().map(this::getMusicPeriodViewDto).collect(Collectors.toList());
    }

    @Override
    public MusicPeriodCreateEditDto getMusicPeriodCreateEditDto(MusicPeriod musicPeriod) {
        return MusicPeriodCreateEditDto.builder()
                .slug(musicPeriod.getSlug())
                .title(musicPeriod.getTitle())
                .titleEn(musicPeriod.getTitleEn())
                .approximateStart(musicPeriod.getApproximateStart())
                .approximateEnd(musicPeriod.getApproximateEnd())
                .description(musicPeriod.getDescription())
                .build();
    }

    private void setFieldsFromDto(MusicPeriod period, MusicPeriodCreateEditDto periodDto) {
        period.setSlug(periodDto.getSlug().trim());
        period.setTitle(periodDto.getTitle().trim());
        period.setTitleEn(periodDto.getTitleEn().trim());
        period.setApproximateStart(periodDto.getApproximateStart());
        period.setApproximateEnd(periodDto.getApproximateEnd());
        period.setDescription(periodDto.getDescription());
    }

    @Override
    public String isSlugExists(String musicPeriodSlug) {
        String message = "";
        if (getBySlug(musicPeriodSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }
}
