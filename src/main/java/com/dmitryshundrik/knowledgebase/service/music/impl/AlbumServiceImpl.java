package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
import com.dmitryshundrik.knowledgebase.mapper.music.AlbumMapper;
import com.dmitryshundrik.knowledgebase.mapper.music.MusicianMapper;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dmitryshundrik.knowledgebase.exception.NotFoundException.ALBUM_NOT_FOUND_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2010s;
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2020s;
import static com.dmitryshundrik.knowledgebase.util.Constants.INVALID_UUID_FORMAT;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_GENRES_CACHE;
import static com.dmitryshundrik.knowledgebase.util.InstantFormatter.instantFormatterDMY;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.baseFormatter;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.formatAlbumSlug;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final MusicianMapper musicianMapper;

    @Override
    public Album getById(String albumId) {
        try {
            UUID uuid = UUID.fromString(albumId);
            return albumRepository.findById(uuid)
                    .orElseThrow(() -> new NotFoundException(ALBUM_NOT_FOUND_MESSAGE.formatted(albumId)));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(INVALID_UUID_FORMAT.formatted(albumId), e);
        }
    }

    @Override
    public Album getBySlug(String albumSlug) {
        return albumRepository.findBySlug(albumSlug)
                .orElseThrow(() -> new NotFoundException(ALBUM_NOT_FOUND_MESSAGE.formatted(albumSlug)));
    }

    @Override
    public List<Album> getAllOrderByCreated() {
        return albumRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Album> getAllByMusician(Musician musician) {
        return albumRepository.findAllByMusician(musician);
    }

    @Override
    public List<Album> getAllByGenre(MusicGenre genre) {
        return albumRepository.findAllByMusicGenresIsContaining(genre);
    }

    @Override
    public List<AlbumSimpleDto> getAllAlbumSimpleDto() {
        return albumRepository.findAllSimpleDtoOrderByCreatedDesc();
    }

    @Override
    public List<AlbumSimpleDto> getAllAlbumSimpleDtoByYear(Integer year) {
        return albumRepository.findAllSimpleDtoByYearOrderByRating(year);
    }

    @Override
    public List<AlbumSimpleDto> getAllAlbumSimpleDtoByDecade(String decade) {
        List<AlbumSimpleDto> albumsByDecade = new ArrayList<>();
        if (DECADE_2010s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllSimpleDtoByDecadeOrderByRating(2009, 2020));
        } else if (DECADE_2020s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllSimpleDtoByDecadeOrderByRating(2019, 2030));
        }
        return albumsByDecade;
    }

    @Override
    public List<AlbumSimpleDto> getTop100BestAlbumSimpleDto() {
        return albumRepository.findTop100BestSimpleDtoOrderByRating();
    }

    @Override
    public List<AlbumViewDto> get10BestAlbumsByYear(Integer year) {
        return getAlbumViewDtoList(albumRepository.findAllByYear(year).stream()
                .sorted(Comparator.comparing(
                        Album::getRating,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .limit(10)
                .collect(Collectors.toList()));
    }

    @Override
    public List<Integer> getAllYearsFromAlbums() {
        return albumRepository.findAllYearsFromAlbums();
    }

    @Override
    public List<Album> getLatestUpdates() {
        return albumRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    @CacheEvict(value = MUSICIAN_GENRES_CACHE, key = "#musician.id")
    public Album createAlbum(AlbumCreateEditDto albumDto, Musician musician, List<Musician> collaborators) {
        Album album = albumMapper.toAlbum(albumDto);
        album.setMusician(musician);
        album.setCollaborators(collaborators);
        album.setMusicGenres(Stream.of(
                Optional.ofNullable(albumDto.getClassicalGenres()).orElse(List.of()),
                Optional.ofNullable(albumDto.getContemporaryGenres()).orElse(List.of())
        ).flatMap(List::stream).toList());
        albumRepository.save(album);
        album.setSlug(formatAlbumSlug(album));
        return album;
    }

    @Override
    public Album updateAlbum(String albumSlug, AlbumCreateEditDto albumDto, List<Musician> collaborators) {
        Album album = getBySlug(albumSlug);
        albumMapper.updateAlbum(album, albumDto);
        album.setCollaborators(collaborators);
        album.setMusicGenres(Stream.of(
                Optional.ofNullable(albumDto.getClassicalGenres()).orElse(List.of()),
                Optional.ofNullable(albumDto.getContemporaryGenres()).orElse(List.of())
        ).flatMap(List::stream).toList());
        album.setSlug(baseFormatter(album.getSlug()));
        return album;
    }

    @Override
    public void deleteAlbum(String slug) {
        albumRepository.delete(getBySlug(slug));
    }

    @Override
    public AlbumViewDto getAlbumViewDto(Album album) {
        AlbumViewDto albumDto = albumMapper.toAlbumViewDto(album);
        albumDto.setCreated(instantFormatterDMY(album.getCreated()));
        albumDto.setCollaborators(musicianMapper.toMusicianSelectDtoList(album.getCollaborators()));
        return albumDto;
    }

    @Override
    public List<AlbumViewDto> getAlbumViewDtoList(List<Album> albumList) {
        return albumList.stream()
                .map(this::getAlbumViewDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<AlbumViewDto> getAlbumViewDtoListOrderBy(List<Album> albumList, SortType sortType) {
        return getAlbumViewDtoList(albumList.stream()
                .sorted((o1, o2) -> {
                            if (SortType.CATALOGUE_NUMBER.equals(sortType)
                                    && o1.getCatalogNumber() != null && o2.getCatalogNumber() != null) {
                                return o1.getCatalogNumber().compareTo(o2.getCatalogNumber());
                            } else if (SortType.RATING.equals(sortType)
                                    && o2.getRating() != null && o1.getRating() != null) {
                                return o2.getRating().compareTo(o1.getRating());
                            } else if (SortType.CREATED.equals(sortType)
                                    && o2.getCreated() != null && (o1.getCreated() != null)) {
                                return o2.getCreated().compareTo(o1.getCreated());
                            } else if (o1.getYear() != null && o2.getYear() != null) {
                                return o1.getYear().compareTo(o2.getYear());
                            }
                            return -1;
                        }
                )
                .toList());
    }

    @Override
    public AlbumCreateEditDto getAlbumCreateEditDto(Album album) {
        AlbumCreateEditDto albumDto = albumMapper.toAlbumCreateEditDto(album);
        albumDto.setCollaboratorsUUID(album.getCollaborators().stream()
                .map(Musician::getId).collect(Collectors.toList()));
        Map<MusicGenreType, List<MusicGenre>> genresByType = Optional.ofNullable(album.getMusicGenres())
                .orElse(List.of())
                .stream()
                .collect(Collectors.groupingBy(MusicGenre::getMusicGenreType));
        albumDto.setContemporaryGenres(genresByType.getOrDefault(MusicGenreType.CONTEMPORARY, List.of()));
        albumDto.setClassicalGenres(genresByType.getOrDefault(MusicGenreType.CLASSICAL, List.of()));
        return albumDto;
    }

    @Override
    public AlbumSelectDto getAlbumSelectDto(Album album) {
        return AlbumSelectDto.builder()
                .id(album.getId().toString())
                .title(album.getTitle())
                .build();
    }

    @Override
    public List<AlbumSelectDto> getAlbumSelectDtoList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumSelectDto).collect(Collectors.toList());
    }

    @Override
    public Long getRepositorySize() {
        return albumRepository.getSize();
    }
}
