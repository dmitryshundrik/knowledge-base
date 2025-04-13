package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreViewDTO;
import com.dmitryshundrik.knowledgebase.util.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
    }

    public MusicGenre getMusicGenreBySlug(String musicGenreSlug) {
        return musicGenreRepository.findBySlug(musicGenreSlug);
    }

    public List<MusicGenre> getAllClassicalGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CLASSICAL);
    }

    public List<MusicGenre> getAllClassicalGenresSortedByTitle() {
        return musicGenreRepository.findAllByMusicGenreTypeOrderByTitle(MusicGenreType.CLASSICAL);
    }

    public List<MusicGenre> getAllContemporaryGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CONTEMPORARY);
    }

    public List<MusicGenre> getAllContemporaryGenresSortedByTitle() {
        return musicGenreRepository.findAllByMusicGenreTypeOrderByTitle(MusicGenreType.CONTEMPORARY);
    }

    public List<MusicGenre> getFilteredClassicalGenres() {
        musicGenreRepository.updateClassicalMusicGenreSetCount();
        return musicGenreRepository.findAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CLASSICAL);
    }

    public List<MusicGenre> getFilteredContemporaryGenres() {
        musicGenreRepository.updateContemporaryMusicGenreSetCount();
        return musicGenreRepository.findAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CONTEMPORARY);
    }

    public String createMusicGenre(MusicGenreCreateEditDTO genreDTO) {
        MusicGenre genre = new MusicGenre();
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
