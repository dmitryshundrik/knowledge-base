package com.dmitryshundrik.knowledgebase.service;

public interface BaseService {

    String isSlugExists(String slug);

    Long getRepositorySize();
}
