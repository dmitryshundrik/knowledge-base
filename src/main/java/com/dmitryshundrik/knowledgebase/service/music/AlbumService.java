package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import java.util.List;

public interface AlbumService {

    Album getBySlug(String albumSlug);
    Album getById(String albumId);

    List<Album> getAllOrderByCreated();
    List<Album> getAllByMusician(Musician musician);
    List<Album> getAllByGenre(MusicGenre genre);
    List<AlbumSimpleDto> getAllAlbumSimpleDto();
    List<AlbumSimpleDto> getAllAlbumSimpleDtoByYear(Integer year);
    List<AlbumSimpleDto> getAllAlbumSimpleDtoByDecade(String decade);
    List<AlbumSimpleDto> getTop100BestAlbumSimpleDto();
    List<AlbumViewDto> get10BestAlbumsByYear(Integer year);
    List<Album> getLatestUpdates();
    List<Integer> getAllYearsFromAlbums();

    Album createAlbum(AlbumCreateEditDto albumDto, Musician musician, List<Musician> collaborators);
    Album updateAlbum(String albumSlug, AlbumCreateEditDto albumDto, List<Musician> collaborators);
    void deleteAlbum(String slug);

    AlbumViewDto getAlbumViewDto(Album album);
    List<AlbumViewDto> getAlbumViewDtoList(List<Album> albumList);
    List<AlbumViewDto> getAlbumViewDtoListOrderBy(List<Album> albumList, SortType sortType);
    AlbumCreateEditDto getAlbumCreateEditDto(Album album);
    AlbumSelectDto getAlbumSelectDto(Album album);
    List<AlbumSelectDto> getAlbumSelectDtoList(List<Album> albumList);

    Long getRepositorySize();
}
