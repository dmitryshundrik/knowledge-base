package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.MusicGenreRepository;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_GENRES_CACHE;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MusicGenreServiceImpl implements MusicGenreService {

    private final MusicGenreRepository musicGenreRepository;

    @Override
    public MusicGenre getBySlug(String musicGenreSlug) {
        return musicGenreRepository.findBySlug(musicGenreSlug);
    }

    @Override
    @Cacheable(value = MUSICIAN_GENRES_CACHE, key = "#musician.id")
    public List<MusicGenre> getAllByMusicianOrderByCount(Musician musician) {
        log.info("Computing genres for musician ID: {}", musician.getId());
        Map<MusicGenre, Long> genreCounts = new HashMap<>();
        List<Album> albums = Optional.ofNullable(musician.getAlbums()).orElse(List.of());
        albums.stream()
                .flatMap(album -> Optional.ofNullable(album.getMusicGenres()).orElse(List.of()).stream())
                .forEach(genre -> genreCounts.merge(genre, 1L, Long::sum));

        List<Composition> compositions = Optional.ofNullable(musician.getCompositions()).orElse(List.of());
        compositions.stream()
                .flatMap(composition -> Optional.ofNullable(composition.getMusicGenres()).orElse(List.of()).stream())
                .forEach(genre -> genreCounts.merge(genre, 1L, Long::sum));

        List<MusicGenre> result = genreCounts.entrySet().stream()
                .sorted(Map.Entry.<MusicGenre, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        log.info("Computed genres for musician ID: {}: {}", musician.getId(), result);
        return result;
    }

    @Override
    public List<MusicGenre> getAllClassicalGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CLASSICAL);
    }

    @Override
    public List<MusicGenre> getAllClassicalGenresOrderByTitle() {
        return musicGenreRepository.findAllByMusicGenreTypeOrderByTitle(MusicGenreType.CLASSICAL);
    }

    @Override
    public List<MusicGenre> getAllContemporaryGenres() {
        return musicGenreRepository.findAllByMusicGenreType(MusicGenreType.CONTEMPORARY);
    }

    @Override
    public List<MusicGenre> getAllContemporaryGenresOrderByTitle() {
        return musicGenreRepository.findAllByMusicGenreTypeOrderByTitle(MusicGenreType.CONTEMPORARY);
    }

    @Override
    public List<MusicGenre> getFilteredClassicalGenres() {
        musicGenreRepository.updateClassicalMusicGenreSetCount();
        return musicGenreRepository.findAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CLASSICAL);
    }

    @Override
    public List<MusicGenre> getFilteredContemporaryGenres() {
        musicGenreRepository.updateContemporaryMusicGenreSetCount();
        return musicGenreRepository.findAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType.CONTEMPORARY);
    }

    @Override
    public MusicGenre createMusicGenre(MusicGenreCreateEditDto genreDto) {
        MusicGenre genre = new MusicGenre();
        setFieldsFromDto(genre, genreDto);
        musicGenreRepository.save(genre);
        return genre;
    }

    @Override
    public MusicGenre updateMusicGenre(String genreSlug, MusicGenreCreateEditDto genreDto) {
        MusicGenre genre = getBySlug(genreSlug);
        setFieldsFromDto(genre, genreDto);
        return genre;
    }

    @Override
    public void deleteMusicGenre(MusicGenre genre) {
        musicGenreRepository.delete(genre);
    }

    @Override
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

    @Override
    public List<MusicGenreViewDto> getMusicGenreViewDtoList(List<MusicGenre> musicGenreList) {
        return musicGenreList.stream().map(this::getMusicGenreViewDto).collect(Collectors.toList());
    }

    @Override
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

    @Override
    public String isSlugExists(String musicGenreSlug) {
        String message = "";
        if (getBySlug(musicGenreSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }
}
