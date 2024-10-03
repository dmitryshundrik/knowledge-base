package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicGenreCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicGenreViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    private final AlbumRepository albumRepository;

    private final CompositionRepository compositionRepository;

    public MusicGenreService(MusicGenreRepository musicGenreRepository, AlbumRepository albumRepository,
                             CompositionRepository compositionRepository) {
        this.musicGenreRepository = musicGenreRepository;
        this.albumRepository = albumRepository;
        this.compositionRepository = compositionRepository;
    }

    @Transactional(readOnly = true)
    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MusicGenre getMusicGenreBySlug(String musicGenreSlug) {
        return musicGenreRepository.getBySlug(musicGenreSlug);
    }

    @Transactional(readOnly = true)
    public List<MusicGenre> getAllClassicalGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MusicGenre> getAllClassicalGenresSortedByTitle() {
        return getAllClassicalGenres().stream()
                .sorted(Comparator.comparing(MusicGenre::getTitle))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MusicGenre> getAllContemporaryGenres() {
        return musicGenreRepository.findAll().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MusicGenre> getAllContemporaryGenresSortedByTitle() {
        return getAllContemporaryGenres().stream()
                .sorted(Comparator.comparing(MusicGenre::getTitle))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MusicGenre> getFilteredClassicalGenres() {
        musicGenreRepository.updateClassicalMusicGenreSetCount();
        return musicGenreRepository.getAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CLASSICAL);
    }

    @Transactional
    public List<MusicGenre> getFilteredContemporaryGenres() {
        musicGenreRepository.updateContemporaryMusicGenreSetCount();
        return musicGenreRepository.getAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CONTEMPORARY);
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

    public MusicGenreViewDTO getMusicGenreViewDTO(MusicGenre genre) {
        return MusicGenreViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(genre.getCreated()))
                .slug(genre.getSlug())
                .title(genre.getTitle())
                .titleEn(genre.getTitleEn())
                .musicGenreType(genre.getMusicGenreType().getLabel())
                .count(genre.getCount())
                .description(genre.getDescription())
                .build();
    }

    public List<MusicGenreViewDTO> getMusicGenreViewDTOList(List<MusicGenre> musicGenreList) {
        return musicGenreList.stream().map(this::getMusicGenreViewDTO).collect(Collectors.toList());
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
        genre.setTitle(genreDTO.getTitle().trim());
        genre.setTitleEn(genreDTO.getTitleEn().trim());
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
