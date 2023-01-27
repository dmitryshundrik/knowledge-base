package com.dmitryshundrik.knowledgebase.service;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.SOTYListComposition;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.repository.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.SOTYRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MusicService {

    @Autowired
    private SOTYRepository SOTYRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    private final static Map<String, Genre> slugGenreMap = new HashMap<>();

    static {
        for (Genre value : Genre.values()) {
            slugGenreMap.put(value.getSlug(), value);
        }
    }

    public List<SOTYList> getAllSOTYLists() {
        return SOTYRepository.findAll();
    }

    public SOTYList getSOTYListBySlug(String slug) {
        return SOTYRepository.getSOTYListBySlug(slug);
    }

    public List<Composition> SOTYListToCompositionList(SOTYList sotyList) {
        List<Composition> compositions = new ArrayList<>();

        List<SOTYListComposition> sotyListCompositions = sotyList.getSotyListCompositions();
        sotyListCompositions.sort(Comparator.comparing(SOTYListComposition::getRank));

        for (SOTYListComposition sotyListComposition : sotyListCompositions) {
            compositions.add(compositionRepository.getCompositionById(sotyListComposition.getId()));
        }
        return compositions;
    }

    public List<Composition> getAllCompositionsByGenre(Genre genre) {
        return compositionRepository.getAllByGenresIsContaining(genre);
    }

    public Genre getGenreBySlug(String slug) {
        return slugGenreMap.get(slug);
    }
}
