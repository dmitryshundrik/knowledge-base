package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.repository.music.MusicPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MusicPeriodService {

    @Autowired
    private MusicPeriodRepository musicPeriodRepository;

    public List<MusicPeriod> getAll() {
        return musicPeriodRepository.findAll();
    }

    public MusicPeriod getMusicPeriodBySlug(String slug) {
        return musicPeriodRepository.getBySlug(slug);
    }
}
