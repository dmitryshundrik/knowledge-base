package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicGenreCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicGenreService {

    @Autowired
    private MusicGenreRepository musicGenreRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CompositionRepository compositionRepository;

    public MusicGenre getMusicGenreBySlug(String musicGenreSlug) {
        return musicGenreRepository.getBySlug(musicGenreSlug);
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
        return musicGenreRepository.getAllByMusicGenreType(MusicGenreType.CLASSICAL).stream()
                .filter(musicGenre -> {
                    Integer count = compositionRepository.countAllByMusicGenresIsContaining(musicGenre);
                    if (count != 0) {
                        musicGenre.setCount(count);
                        return true;
                    }
                    return false;
                })
                .sorted((o1, o2) -> o2.getCount().compareTo(o1.getCount())).collect(Collectors.toList());
    }

    public List<MusicGenre> getFilteredContemporaryGenres() {
        return musicGenreRepository.getAllByMusicGenreType(MusicGenreType.CONTEMPORARY).stream()
                .filter(musicGenre -> {
                    Integer count = albumRepository.countAllByMusicGenresIsContaining(musicGenre);
                    if (count != 0) {
                        musicGenre.setCount(count);
                        return true;
                    }
                    return false;
                })
                .sorted((o1, o2) -> o2.getCount().compareTo(o1.getCount())).collect(Collectors.toList());
    }
    public String createMusicGenre(MusicGenreCreateEditDTO genreDTO) {
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
        genre.setSlug(genreDTO.getSlug().trim());
        genre.setTitle(genreDTO.getTitle());
        genre.setTitleEn(genreDTO.getTitleEn());
        genre.setMusicGenreType(genreDTO.getMusicGenreType());
        genre.setDescription(genreDTO.getDescription());
    }

    public String musicGenreSlugIsExist(String musicGenreSlug) {
        String message = "";
        if (getMusicGenreBySlug(musicGenreSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

}
