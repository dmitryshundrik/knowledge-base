package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicPeriodViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.MusicPeriodRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicPeriodService {

    @Autowired
    private MusicPeriodRepository musicPeriodRepository;

    public MusicPeriod getMusicPeriodBySlug(String musicPeriodSlug) {
        return musicPeriodRepository.getBySlug(musicPeriodSlug);
    }

    public List<MusicPeriod> getAll() {
        return musicPeriodRepository.findAll();
    }

    public List<MusicPeriod> getAllSortedByStart() {
        return getAll().stream()
                .sorted(Comparator.comparing(MusicPeriod::getApproximateStart)).collect(Collectors.toList());
    }

    public String createMusicPeriod(MusicPeriodCreateEditDTO periodDTO) {
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

    public MusicPeriodViewDTO getMusicPeriodViewDTO(MusicPeriod musicPeriod) {
        return MusicPeriodViewDTO.builder()
                .created(Formatter.instantFormatterYMD(musicPeriod.getCreated()))
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
        period.setTitle(periodDTO.getTitle());
        period.setTitleEn(periodDTO.getTitleEn());
        period.setApproximateStart(periodDTO.getApproximateStart());
        period.setApproximateEnd(periodDTO.getApproximateEnd());
        period.setDescription(periodDTO.getDescription());
    }

    public String musicPeriodSlugIsExist(String musicPeriodSlug) {
        String message = "";
        if (getMusicPeriodBySlug(musicPeriodSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

}
