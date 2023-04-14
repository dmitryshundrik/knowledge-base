package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public Album getAlbumById(String id) {
        return albumRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public Album getAlbumBySlug(String slug) {
        return albumRepository.getAlbumBySlug(slug);
    }

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public List<Album> getAllWithRating() {
        return albumRepository.getAllByRatingIsNotNull();
    }

    public List<Album> getAllAlbumsSortedByCreated() {
        return getAllAlbums().stream()
                .sorted((o1, o2) -> {
                    if (o1.getCreated() != null && o2.getCreated() != null) {
                        return o1.getCreated().compareTo(o2.getCreated());
                    }
                    return -1;
                })
                .collect(Collectors.toList());
    }

    public List<Album> getAllAlbumsByYear(Integer year) {
        return albumRepository.getAllByYear(year);
    }

    public List<Album> getAllAlbumsByMusician(Musician musician) {
        return albumRepository.getAllByMusician(musician);
    }

    public List<Album> getAllAlbumsByPeriod(MusicPeriod period) {
        return albumRepository.getAllByMusicPeriodsIsContaining(period);
    }

    public List<Album> getAllAlbumsByGenre(MusicGenre genre) {
        return albumRepository.getAllByMusicGenresIsContaining(genre);
    }

    public List<AlbumViewDTO> getAllAlbumsForAOTYList(Integer year) {
        return getAlbumViewDTOList(albumRepository.getAllByYearAndYearEndRankNotNull(year).stream()
                .sorted(Comparator.comparing(Album::getYearEndRank))
                .collect(Collectors.toList()));
    }

    public List<AlbumViewDTO> get10BestAlbumsByYear(Integer year) {
        return getAlbumViewDTOList(albumRepository.getAllByYear(year).stream()
                .sorted((o1, o2) -> o2.getRating().compareTo(o1.getRating()))
                .limit(10)
                .collect(Collectors.toList()));
    }

    public List<AlbumViewDTO> getTop100BestAlbums() {
        return getAlbumViewDTOList(getAllWithRating().stream()
                .sorted((o1, o2) -> o2.getRating().compareTo(o1.getRating()))
                .limit(100)
                .collect(Collectors.toList()));
    }

    public List<Integer> getAllYearsFromAlbums() {
        return albumRepository.getAllYearsFromAlbums();
    }

    public AlbumCreateEditDTO createAlbumByDTO(AlbumCreateEditDTO albumDTO, Musician musician) {
        Album album = new Album();
        album.setCreated(Instant.now());
        album.setMusician(musician);
        setFieldsFromDTO(album, albumDTO);
        Album createdAlbum = albumRepository.save(album);
        createdAlbum.setSlug(createdAlbum.getSlug() + "-" + createdAlbum.getId());
        return getAlbumCreateEditDTO(createdAlbum);
    }

    public void updateAlbum(String albumSlug, AlbumCreateEditDTO albumDTO) {
        Album albumBySlug = getAlbumBySlug(albumSlug);
        setFieldsFromDTO(albumBySlug, albumDTO);
    }

    public void deleteAlbumBySlug(String slug) {
        albumRepository.delete(getAlbumBySlug(slug));
    }

    public List<AlbumViewDTO> getEssentialAlbumsViewDTOList(List<Album> albumList) {
        return albumList.stream().map(album -> getAlbumViewDTO(album))
                .filter(albumViewDTO -> albumViewDTO.getEssentialAlbumsRank() != null)
                .sorted(Comparator.comparing(AlbumViewDTO::getEssentialAlbumsRank))
                .collect(Collectors.toList());
    }

    public AlbumViewDTO getAlbumViewDTO(Album album) {
        return AlbumViewDTO.builder()
                .created(Formatter.instantFormatterYMDHMS(album.getCreated()))
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .feature(album.getFeature())
                .artwork(album.getArtwork())
                .year(album.getYear())
                .musicPeriods(album.getMusicPeriods())
                .musicGenres(album.getMusicGenres())
                .rating(album.getRating())
                .yearEndRank(album.getYearEndRank())
                .essentialAlbumsRank(album.getEssentialAlbumsRank())
                .highlights(album.getHighlights())
                .description(album.getDescription())
                .build();
    }

    public List<AlbumViewDTO> getAlbumViewDTOList(List<Album> albumList) {
        return albumList.stream().map(album -> getAlbumViewDTO(album)).collect(Collectors.toList());

    }

    public AlbumCreateEditDTO getAlbumCreateEditDTO(Album album) {
        return AlbumCreateEditDTO.builder()
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .feature(album.getFeature())
                .artwork(album.getArtwork())
                .year(album.getYear())
                .musicPeriods(album.getMusicPeriods())
                .classicalGenres(album.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                        .collect(Collectors.toList()))
                .contemporaryGenres(album.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                        .collect(Collectors.toList()))
                .rating(album.getRating())
                .yearEndRank(album.getYearEndRank())
                .essentialAlbumsRank(album.getEssentialAlbumsRank())
                .highlights(album.getHighlights())
                .description(album.getDescription())
                .build();
    }

    public AlbumSelectDTO getAlbumSelectDTO(Album album) {
        return AlbumSelectDTO.builder()
                .id(album.getId().toString())
                .title(album.getTitle())
                .build();
    }

    public List<AlbumSelectDTO> getAlbumSelectDTOList(List<Album> albumList) {
        return albumList.stream().map(album -> getAlbumSelectDTO(album)).collect(Collectors.toList());
    }

    public List<AlbumViewDTO> getSortedAlbumViewDTOList(List<Album> albumList, SortType sortType) {
        return getAlbumViewDTOList(albumList).stream()
                .sorted((o1, o2) -> {
                            if (SortType.CATALOGUE_NUMBER.equals(sortType)
                                    && o1.getCatalogNumber() != null && o2.getCatalogNumber() != null) {
                                return o1.getCatalogNumber().compareTo(o2.getCatalogNumber());
                            } else if (SortType.RATING.equals(sortType)
                                    && o2.getRating() != null && o1.getRating() != null) {
                                return o2.getRating().compareTo(o1.getRating());
                            } else if (o1.getYear() != null && o2.getYear() != null) {
                                return o1.getYear().compareTo(o2.getYear());
                            }
                            return -1;
                        }
                )
                .collect(Collectors.toList());
    }

    private void setFieldsFromDTO(Album album, AlbumCreateEditDTO albumDTO) {
        album.setSlug(albumDTO.getSlug().trim());
        album.setTitle(albumDTO.getTitle());
        album.setCatalogNumber(albumDTO.getCatalogNumber());
        album.setFeature(albumDTO.getFeature());
        album.setYear(albumDTO.getYear());
        album.setMusicPeriods(albumDTO.getMusicPeriods());

        List<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(albumDTO.getClassicalGenres());
        musicGenres.addAll(albumDTO.getContemporaryGenres());

        album.setMusicGenres(musicGenres);
        album.setRating(albumDTO.getRating());
        album.setYearEndRank(albumDTO.getYearEndRank());
        album.setEssentialAlbumsRank(albumDTO.getEssentialAlbumsRank());
        album.setHighlights(albumDTO.getHighlights());
        album.setDescription(albumDTO.getDescription());
    }

    public List<AlbumViewDTO> getLatestUpdate() {
        return getAlbumViewDTOList(albumRepository.findFirst10ByOrderByCreated());
    }

}
