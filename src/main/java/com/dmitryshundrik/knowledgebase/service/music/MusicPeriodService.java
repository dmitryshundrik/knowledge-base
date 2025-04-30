package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicPeriodViewDto;
import com.dmitryshundrik.knowledgebase.repository.music.MusicPeriodRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicPeriodService {

    private final MusicPeriodRepository musicPeriodRepository;

    public MusicPeriod getBySlug(String musicPeriodSlug) {
        return musicPeriodRepository.findBySlug(musicPeriodSlug);
    }

    public List<MusicPeriod> getAll() {
        return musicPeriodRepository.findAll();
    }

    public List<MusicPeriod> getAllOrderByStart() {
        return musicPeriodRepository.findAllByOrderByApproximateStartAsc();
    }

    public List<MusicPeriod> getMusiciansAllOrderByStart(List<MusicPeriod> musicPeriodList) {
        musicPeriodList.sort(Comparator.comparing(MusicPeriod::getApproximateStart));
        return musicPeriodList;
    }

    public String createMusicPeriod(MusicPeriodCreateEditDto periodDTO) {
        MusicPeriod period = new MusicPeriod();
        setFieldsFromDto(period, periodDTO);
        musicPeriodRepository.save(period);
        return period.getSlug();
    }

    public String updateMusicPeriod(String periodSlug, MusicPeriodCreateEditDto periodDTO) {
        MusicPeriod period = getBySlug(periodSlug);
        setFieldsFromDto(period, periodDTO);
        return periodDTO.getSlug();
    }

    public void deleteMusicPeriod(MusicPeriod period) {
        musicPeriodRepository.delete(period);
    }

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

    public List<MusicPeriodViewDto> getMusicPeriodViewDtoList(List<MusicPeriod> musicPeriodList) {
        return musicPeriodList.stream().map(this::getMusicPeriodViewDto).collect(Collectors.toList());
    }

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

    public String isSlugExists(String musicPeriodSlug) {
        String message = "";
        if (getBySlug(musicPeriodSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }
}
