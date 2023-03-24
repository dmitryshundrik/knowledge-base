package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicGenreCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicGenreService {

    @Autowired
    private MusicGenreRepository musicGenreRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    public MusicGenre getMusicGenreBySlug(String slug) {
        return musicGenreRepository.getBySlug(slug);
    }

    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
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

    public List<MusicGenre> getFilteredClassicalGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> {
                    List<Composition> compositions = compositionRepository.getAllByMusicGenresIsContaining(musicGenre);
                    if (musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL) && !compositions.isEmpty()) {
                        musicGenre.setCount(compositions.size());
                        return true;
                    }
                    return false;
                })
                .sorted(Comparator.comparing(MusicGenre::getCount)).collect(Collectors.toList());
    }

    public List<MusicGenre> getFilteredContemporaryGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> {
                    List<Composition> compositions = compositionRepository.getAllByMusicGenresIsContaining(musicGenre);
                    if (musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY) && !compositions.isEmpty()) {
                        musicGenre.setCount(compositions.size());
                        return true;
                    }
                    return false;
                })
                .sorted(Comparator.comparing(MusicGenre::getCount)).collect(Collectors.toList());
    }

    public String createMusicGenreByDTO(MusicGenreCreateEditDTO genreDTO) {
        MusicGenre genre = new MusicGenre();
        genre.setCreated(Instant.now());
        setFieldsFromDTO(genre, genreDTO);
        musicGenreRepository.save(genre);
        return genre.getSlug();
    }

    public String updateMusicGenre(String genreSlug, MusicGenreCreateEditDTO genreDTO) {
        MusicGenre genre = getMusicGenreBySlug(genreSlug);
        setFieldsFromDTO(genre, genreDTO);
        return genreDTO.getSlug();
    }

    public void deleteMusicGenre(MusicGenre genre) {
        musicGenreRepository.delete(genre);
    }

    public MusicGenreCreateEditDTO getMusicGenreCreateEditDTO(MusicGenre genre) {
        return MusicGenreCreateEditDTO.builder()
                .slug(genre.getSlug())
                .title(genre.getTitle())
                .titleEn(genre.getTitleEn())
                .musicGenreType(genre.getMusicGenreType())
                .description(genre.getDescription())
                .build();
    }

    public void setFieldsFromDTO(MusicGenre genre, MusicGenreCreateEditDTO genreDTO) {
        genre.setSlug(genreDTO.getSlug());
        genre.setTitle(genreDTO.getTitle());
        genre.setTitleEn(genreDTO.getTitleEn());
        genre.setMusicGenreType(genreDTO.getMusicGenreType());
        genre.setDescription(genreDTO.getDescription());
    }

}
