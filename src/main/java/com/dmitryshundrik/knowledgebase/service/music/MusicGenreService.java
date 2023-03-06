package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicGenreService {

    @Autowired
    private MusicGenreRepository musicGenreRepository;

    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
    }

    public MusicGenre getMusicGenreBySlug(String slug) {
        return musicGenreRepository.getBySlug(slug);
    }

    public List<MusicGenre> getAllClassicalGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                .collect(Collectors.toList());
    }

    public List<MusicGenre> getAllContemporaryGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                .collect(Collectors.toList());
    }

}
