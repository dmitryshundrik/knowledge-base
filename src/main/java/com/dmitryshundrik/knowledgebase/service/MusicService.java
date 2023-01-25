package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.SOTYListComposition;
import com.dmitryshundrik.knowledgebase.repository.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.SOTYRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MusicService {

    @Autowired
    private SOTYRepository SOTYRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    public List<SOTYList> findAllSOTYLists() {
        return SOTYRepository.findAll();
    }

    public SOTYList findSOTYListByUrl(String url) {
        return SOTYRepository.findSOTYListByUrl(url);
    }

    public List<Composition> SOTYListToCompositionList(SOTYList sotyList) {
        List<Composition> compositions = new ArrayList<>();

        List<SOTYListComposition> sotyListCompositions = sotyList.getSotyListCompositions();
        sotyListCompositions.sort(Comparator.comparing(SOTYListComposition::getRank));

        for (SOTYListComposition sotyListComposition : sotyListCompositions) {
            compositions.add(compositionRepository.findCompositionById(sotyListComposition.getId()));
        }
        return compositions;
    }
}
