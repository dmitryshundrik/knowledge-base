package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.repository.music.SOTYListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SOTYListService {

    @Autowired
    private SOTYListRepository sotyListRepository;

    public List<SOTYList> getAllSOTYLists() {
        return sotyListRepository.findAll();
    }

    public SOTYList getSOTYListBySlug(String slug) {
        return sotyListRepository.getSOTYListBySlug(slug);
    }
}
