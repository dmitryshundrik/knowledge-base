package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SOTYListRepository extends JpaRepository<SOTYList, UUID> {

    SOTYList getSOTYListBySlug(String slug);

}
