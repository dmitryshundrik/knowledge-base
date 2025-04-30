package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentEvent;

import java.util.List;

public interface EntityEventService {

    List<EntityCurrentEvent> getEntityEvents(Integer dayInterval);

    List<EntityCurrentEvent> processEntityEvents(Integer dayInterval);
}
