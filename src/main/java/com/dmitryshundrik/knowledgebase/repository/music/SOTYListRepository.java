package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SOTYListRepository extends JpaRepository<SOTYList, Long> {

    SOTYList getSOTYListBySlug(String slug);

}