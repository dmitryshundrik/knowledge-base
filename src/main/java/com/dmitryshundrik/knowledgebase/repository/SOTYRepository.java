package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SOTYRepository extends JpaRepository<SOTYList, Long> {

    SOTYList findSOTYListByUrl(String url);

}
