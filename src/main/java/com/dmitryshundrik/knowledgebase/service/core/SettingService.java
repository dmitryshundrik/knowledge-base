package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Setting;
import com.dmitryshundrik.knowledgebase.model.dto.core.SettingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.core.SettingViewDto;
import com.dmitryshundrik.knowledgebase.repository.core.SettingRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.LIMIT_FOR_UPDATES;
import static com.dmitryshundrik.knowledgebase.util.Constants.TIME_INTERVAL_FOR_UPDATES;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {

    private final SettingRepository settingRepository;

    public Setting getById(String settingId) {
        return settingRepository.findById(UUID.fromString(settingId)).orElse(null);
    }

    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    public SettingViewDto updateSetting(String settingId, SettingCreateEditDto settingDTO) {
        Setting byId = getById(settingId);
        byId.setName(settingDTO.getName().trim());
        byId.setValue(settingDTO.getValue().trim());
        return getSettingViewDto(byId);
    }

    public void deleteSetting(String settingId) {
        Setting byId = getById(settingId);
        settingRepository.delete(byId);
    }

    public SettingViewDto getSettingViewDto(Setting setting) {
        return SettingViewDto.builder()
                .id(setting.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(setting.getCreated()))
                .name(setting.getName())
                .value(setting.getValue())
                .build();
    }

    public List<SettingViewDto> getSettingViewDtoList(List<Setting> settingList) {
        return settingList.stream()
                .map(this::getSettingViewDto)
                .collect(Collectors.toList());
    }

    public SettingCreateEditDto getSettingCreateEditDto(Setting setting) {
        return SettingCreateEditDto.builder()
                .id(setting.getId().toString())
                .name(setting.getName())
                .value(setting.getValue())
                .build();
    }

    public Integer getTimeIntervalForUpdates() {
        return Integer.valueOf(settingRepository.findByName(TIME_INTERVAL_FOR_UPDATES).getValue());
    }

    public Integer getLimitForUpdates() {
        return Integer.valueOf(settingRepository.findByName(LIMIT_FOR_UPDATES).getValue());
    }
}
