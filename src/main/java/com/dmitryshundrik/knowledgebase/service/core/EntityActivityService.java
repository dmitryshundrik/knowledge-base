package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.EntityCurrentActivity;

import java.util.List;

public interface EntityActivityService {

    List<EntityCurrentActivity> getEntityActivities();

    List<EntityCurrentActivity> processEntityActivities();
}
