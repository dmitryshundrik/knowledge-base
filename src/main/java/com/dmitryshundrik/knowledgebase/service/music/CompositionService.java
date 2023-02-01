package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }

    public List<Composition> getAllCompositionsByPeriod(Period period) {
        return compositionRepository.getAllByPeriod(period);
    }

    public List<Composition> getAllCompositionsByGenre(Genre genre) {
        return compositionRepository.getAllByGenresIsContaining(genre);
    }

    public List<Composition> getAllCompositionsBySOTYList(SOTYList sotyList) {
        return compositionRepository.getAllByYearAndYearEndRankNotNull(sotyList.getYear());
    }

}
