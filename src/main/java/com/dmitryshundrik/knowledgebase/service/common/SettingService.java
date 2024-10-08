package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.Setting;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingViewDTO;
import com.dmitryshundrik.knowledgebase.repository.common.SettingRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.LIMIT_FOR_UPDATES;
import static com.dmitryshundrik.knowledgebase.util.Constants.TIME_INTERVAL_FOR_UPDATES;

@Service
@Transactional
public class SettingService {

    private final SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Transactional(readOnly = true)
    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Setting getById(String settingId) {
        return settingRepository.getById(UUID.fromString(settingId));
    }

    public SettingViewDTO updateSetting(String settingId, SettingCreateEditDTO settingDTO) {
        Setting byId = getById(settingId);
        byId.setName(settingDTO.getName().trim());
        byId.setValue(settingDTO.getValue().trim());
        return getSettingViewDTO(byId);
    }

    public SettingViewDTO getSettingViewDTO(Setting setting) {
        return SettingViewDTO.builder()
                .id(setting.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(setting.getCreated()))
                .name(setting.getName())
                .value(setting.getValue())
                .build();
    }

    public List<SettingViewDTO> getSettingViewDTOList(List<Setting> settingList) {
        return settingList.stream()
                .map(this::getSettingViewDTO)
                .collect(Collectors.toList());
    }

    public SettingCreateEditDTO getSettingCreateEditDTO(Setting setting) {
        return SettingCreateEditDTO.builder()
                .id(setting.getId().toString())
                .name(setting.getName())
                .value(setting.getValue())
                .build();
    }

    public Integer getTimeIntervalForUpdates() {
        return Integer.valueOf(settingRepository.getByName(TIME_INTERVAL_FOR_UPDATES).getValue());
    }

    public Integer getLimitForUpdates() {
        return Integer.valueOf(settingRepository.getByName(LIMIT_FOR_UPDATES).getValue());
    }

    public void deleteSettingById(String settingId) {
        Setting byId = getById(settingId);
        settingRepository.delete(byId);
    }
}
