package com.dmitryshundrik.knowledgebase.service;

public interface BaseService {

    String isSlugExist(String slug);

    Long getRepositorySize();
}
