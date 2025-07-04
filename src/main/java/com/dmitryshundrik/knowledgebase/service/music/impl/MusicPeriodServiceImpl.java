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

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.MUSIC_PERIOD_NOT_FOUND_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD_MUST_NOT_BE_NULL;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicPeriodServiceImpl implements MusicPeriodService {

    private final MusicPeriodRepository musicPeriodRepository;

    @Override
    public MusicPeriod getBySlug(String musicPeriodSlug) {
        return musicPeriodRepository.findBySlug(musicPeriodSlug)
                .orElseThrow(() -> new IllegalArgumentException(MUSIC_PERIOD_NOT_FOUND_MESSAGE.formatted(musicPeriodSlug)));
    }

    @Override
    public List<MusicPeriod> getAllOrderByStart() {
        return musicPeriodRepository.findAllByOrderByApproximateStartAsc();
    }

    @Override
    public MusicPeriod createMusicPeriod(MusicPeriodCreateEditDto periodDto) {
        MusicPeriod period = new MusicPeriod();
        setFieldsFromDto(period, periodDto);
        musicPeriodRepository.save(period);
        return period;
    }

    @Override
    public MusicPeriod updateMusicPeriod(String periodSlug, MusicPeriodCreateEditDto periodDto) {
        MusicPeriod period = getBySlug(periodSlug);
        setFieldsFromDto(period, periodDto);
        return period;
    }

    @Override
    public void deleteMusicPeriod(MusicPeriod period) {
        musicPeriodRepository.delete(period);
    }

    @Override
    public MusicPeriodViewDto getMusicPeriodViewDto(MusicPeriod musicPeriod) {
        if (musicPeriod == null) {
            throw new IllegalArgumentException(MUSIC_PERIOD_MUST_NOT_BE_NULL);
        }
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
        if (musicPeriod == null) {
            throw new IllegalArgumentException(MUSIC_PERIOD_MUST_NOT_BE_NULL);
        }
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
        if (musicPeriodRepository.findBySlug(musicPeriodSlug).isPresent()) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }
}
