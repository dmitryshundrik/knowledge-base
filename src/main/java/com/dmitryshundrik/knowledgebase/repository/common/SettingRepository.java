package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.common.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {

    Setting getById(UUID settingId);

    Setting getByName(String settingName);

}
