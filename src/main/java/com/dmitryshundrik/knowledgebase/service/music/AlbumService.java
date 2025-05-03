package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;

import java.util.List;

public interface AlbumService {

    Album getBySlug(String albumSlug);
    Album getById(String albumId);

    List<Album> getAll();
    List<Album> getAllOrderByCreatedDesc();
    List<Album> getAllByMusician(Musician musician);
    List<Album> getAllByYear(Integer year);
    List<Album> getAllByDecade(String decade);
    List<Album> getAllByGenre(MusicGenre genre);
    List<AlbumViewDto> getTop100BestAlbums();
    List<AlbumViewDto> get10BestAlbumsByYear(Integer year);
    List<Integer> getAllYearsFromAlbums();
    List<Album> getLatestUpdates();

    AlbumViewDto createAlbum(AlbumCreateEditDto albumDto, Musician musician, List<Musician> collaborators);
    AlbumViewDto updateAlbum(String albumSlug, AlbumCreateEditDto albumDto, List<Musician> collaborators);
    void deleteAlbum(String slug);

    List<AlbumViewDto> getEssentialAlbumsViewDtoList(List<Album> albumList);
    AlbumViewDto getAlbumViewDto(Album album);
    List<AlbumViewDto> getAlbumViewDtoList(List<Album> albumList);
    AlbumCreateEditDto getAlbumCreateEditDto(Album album);
    AlbumSelectDto getAlbumSelectDto(Album album);
    List<AlbumSelectDto> getAlbumSelectDtoList(List<Album> albumList);
    List<AlbumViewDto> getSortedAlbumViewDtoList(List<Album> albumList, SortType sortType);

    Long getRepositorySize();
}
