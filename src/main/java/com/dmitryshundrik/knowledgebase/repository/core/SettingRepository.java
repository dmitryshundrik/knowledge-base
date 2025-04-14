package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {

    Setting findByName(String settingName);
}
