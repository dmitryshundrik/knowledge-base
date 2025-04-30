package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentEvent;
import java.util.List;

public interface EntityNotificationService {

    List<EntityCurrentEvent> getCurrentNotifications(Integer dayInterval);
}
