package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.Setting;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.common.dto.SettingViewDTO;
import com.dmitryshundrik.knowledgebase.repository.common.SettingRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    public List<Setting> getAll() {
        return settingRepository.findAll();
    }

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
                .map(setting -> getSettingViewDTO(setting))
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
        return Integer.valueOf(settingRepository.getByName("TIME_INTERVAL_FOR_UPDATES").getValue());
    }

    public Integer getLimitForUpdates() {
        return Integer.valueOf(settingRepository.getByName("LIMIT_FOR_UPDATES").getValue());
    }

    public void deleteSettingById(String settingId) {
        Setting byId = getById(settingId);
        settingRepository.delete(byId);
    }

}
