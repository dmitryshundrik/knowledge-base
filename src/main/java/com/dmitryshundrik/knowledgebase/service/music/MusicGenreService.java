package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
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

    public MusicGenre getBySlug(String musicGenreSlug) {
        return musicGenreRepository.findBySlug(musicGenreSlug);
    }

    public List<MusicGenre> getAll() {
        return musicGenreRepository.findAll();
    }

    public List<MusicGenre> getAllClassicalGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CLASSICAL);
    }

    public List<MusicGenre> getAllClassicalGenresOrderByTitle() {
        return musicGenreRepository.findAllByMusicGenreTypeOrderByTitle(MusicGenreType.CLASSICAL);
    }

    public List<MusicGenre> getAllContemporaryGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CONTEMPORARY);
    }

    public List<MusicGenre> getAllContemporaryGenresOrderByTitle() {
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

    public String createMusicGenre(MusicGenreCreateEditDto genreDto) {
        MusicGenre genre = new MusicGenre();
        setFieldsFromDto(genre, genreDto);
        musicGenreRepository.save(genre);
        return genre.getSlug();
    }

    public String updateMusicGenre(String genreSlug, MusicGenreCreateEditDto genreDto) {
        MusicGenre genre = getBySlug(genreSlug);
        setFieldsFromDto(genre, genreDto);
        return genreDto.getSlug();
    }

    public void deleteMusicGenre(MusicGenre genre) {
        musicGenreRepository.delete(genre);
    }

    public MusicGenreViewDto getMusicGenreViewDto(MusicGenre genre) {
        return MusicGenreViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(genre.getCreated()))
                .slug(genre.getSlug())
                .title(genre.getTitle())
                .titleEn(genre.getTitleEn())
                .musicGenreType(genre.getMusicGenreType().getLabel())
                .count(genre.getCount())
                .description(genre.getDescription())
                .build();
    }

    public List<MusicGenreViewDto> getMusicGenreViewDtoList(List<MusicGenre> musicGenreList) {
        return musicGenreList.stream().map(this::getMusicGenreViewDto).collect(Collectors.toList());
    }

    public MusicGenreCreateEditDto getMusicGenreCreateEditDto(MusicGenre genre) {
        return MusicGenreCreateEditDto.builder()
                .slug(genre.getSlug())
                .title(genre.getTitle())
                .titleEn(genre.getTitleEn())
                .musicGenreType(genre.getMusicGenreType())
                .description(genre.getDescription())
                .build();
    }

    public void setFieldsFromDto(MusicGenre genre, MusicGenreCreateEditDto genreDto) {
        genre.setSlug(genreDto.getSlug().trim());
        genre.setTitle(genreDto.getTitle().trim());
        genre.setTitleEn(genreDto.getTitleEn().trim());
        genre.setMusicGenreType(genreDto.getMusicGenreType());
        genre.setDescription(genreDto.getDescription());
    }

    public String isSlugExists(String musicGenreSlug) {
        String message = "";
        if (getBySlug(musicGenreSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }
}
