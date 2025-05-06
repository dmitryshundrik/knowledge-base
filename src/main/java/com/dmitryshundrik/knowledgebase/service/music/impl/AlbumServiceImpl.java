package com.dmitryshundrik.knowledgebase.service.music.impl;

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
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2010s;
import static com.dmitryshundrik.knowledgebase.util.Constants.DECADE_2020s;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_GENRES_CACHE;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private final MusicianMapper musicianMapper;

    @Override
    public Album getById(String albumId) {
        return albumRepository.findById(UUID.fromString(albumId)).orElse(null);
    }

    @Override
    public Album getBySlug(String albumSlug) {
        return albumRepository.findBySlug(albumSlug);
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
        return albumRepository.findAllAlbumSimpleDtoOrderByCreatedDesc();
    }

    @Override
    public List<AlbumSimpleDto> getAllAlbumSimpleDtoByYear(Integer year) {
        return albumRepository.findAllAlbumSimpleDtoByYearOrderByRating(year);
    }

    @Override
    public List<AlbumSimpleDto> getAllAlbumSimpleDtoByDecade(String decade) {
        List<AlbumSimpleDto> albumsByDecade = new ArrayList<>();
        if (DECADE_2010s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllAlbumSimpleDtoByDecadeOrderByRating(2009, 2020));
        } else if (DECADE_2020s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllAlbumSimpleDtoByDecadeOrderByRating(2019, 2030));
        }
        return albumsByDecade;
    }

    @Override
    public List<AlbumViewDto> getTop100BestAlbums() {
        return getAlbumViewDtoList(albumRepository.findFirst100ByRatingIsNotNullOrderByRatingDesc());
    }

    @Override
    public List<AlbumViewDto> get10BestAlbumsByYear(Integer year) {
        return getAlbumViewDtoList(albumRepository.findAllByYear(year).stream()
                .sorted((o1, o2) -> o2.getRating().compareTo(o1.getRating()))
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
    public AlbumViewDto createAlbum(AlbumCreateEditDto albumDto, Musician musician, List<Musician> collaborators) {
        Album album = albumMapper.toAlbum(albumDto);
        album.setSlug(musician.getSlug() + "-" + SlugFormatter.slugFormatter(album.getSlug()));
        album.setMusician(musician);
        album.setCollaborators(collaborators);
        album.setMusicGenres(Stream.of(
                Optional.ofNullable(albumDto.getClassicalGenres()).orElse(List.of()),
                Optional.ofNullable(albumDto.getContemporaryGenres()).orElse(List.of())
        ).flatMap(List::stream).toList());
        return getAlbumViewDto(albumRepository.save(album));
    }

    @Override
    public AlbumViewDto updateAlbum(String albumSlug, AlbumCreateEditDto albumDto, List<Musician> collaborators) {
        Album album = getBySlug(albumSlug);
        albumMapper.updateAlbum(album, albumDto);
        album.setCollaborators(collaborators);
        album.setMusicGenres(Stream.of(
                Optional.ofNullable(albumDto.getClassicalGenres()).orElse(List.of()),
                Optional.ofNullable(albumDto.getContemporaryGenres()).orElse(List.of())
        ).flatMap(List::stream).toList());
        return getAlbumViewDto(album);
    }

    @Override
    public void deleteAlbum(String slug) {
        albumRepository.delete(getBySlug(slug));
    }

    @Override
    public AlbumViewDto getAlbumViewDto(Album album) {
        AlbumViewDto albumDto = albumMapper.toAlbumViewDto(album);
        albumDto.setCreated(InstantFormatter.instantFormatterDMY(album.getCreated()));
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
    public List<AlbumViewDto> getEssentialAlbumViewDtoList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumViewDto)
                .filter(albumViewDto -> albumViewDto.getEssentialAlbumsRank() != null)
                .sorted(Comparator.comparing(AlbumViewDto::getEssentialAlbumsRank))
                .collect(Collectors.toList());
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
