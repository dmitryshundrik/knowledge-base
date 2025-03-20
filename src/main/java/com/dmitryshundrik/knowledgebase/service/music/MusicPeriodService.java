package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.dto.music.MusicPeriodCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.MusicPeriodViewDTO;
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

    public List<MusicPeriod> getAll() {
        return musicPeriodRepository.findAll();
    }

    public List<MusicPeriod> getAllSortedByStart() {
        return musicPeriodRepository.findAllByOrderByApproximateStartAsc();
    }

    public MusicPeriod getMusicPeriodBySlug(String musicPeriodSlug) {
        return musicPeriodRepository.findBySlug(musicPeriodSlug);
    }

    public List<MusicPeriod> getSortedByStart(List<MusicPeriod> musicPeriodList) {
        musicPeriodList.sort(Comparator.comparing(MusicPeriod::getApproximateStart));
        return musicPeriodList;
    }

    public String createMusicPeriod(MusicPeriodCreateEditDTO periodDTO) {
        MusicPeriod period = new MusicPeriod();
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

    public MusicPeriodViewDTO getMusicPeriodViewDTO(MusicPeriod musicPeriod) {
        return MusicPeriodViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(musicPeriod.getCreated()))
                .slug(musicPeriod.getSlug())
                .title(musicPeriod.getTitle())
                .titleEn(musicPeriod.getTitleEn())
                .approximateStart(musicPeriod.getApproximateStart())
                .approximateEnd(musicPeriod.getApproximateEnd())
                .description(musicPeriod.getDescription())
                .build();
    }

    public List<MusicPeriodViewDTO> getMusicPeriodViewDTOList(List<MusicPeriod> musicPeriodList) {
        return musicPeriodList.stream().map(this::getMusicPeriodViewDTO).collect(Collectors.toList());
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
        period.setSlug(periodDTO.getSlug().trim());
        period.setTitle(periodDTO.getTitle().trim());
        period.setTitleEn(periodDTO.getTitleEn().trim());
        period.setApproximateStart(periodDTO.getApproximateStart());
        period.setApproximateEnd(periodDTO.getApproximateEnd());
        period.setDescription(periodDTO.getDescription());
    }

    public String musicPeriodSlugIsExist(String musicPeriodSlug) {
        String message = "";
        if (getMusicPeriodBySlug(musicPeriodSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }
}
