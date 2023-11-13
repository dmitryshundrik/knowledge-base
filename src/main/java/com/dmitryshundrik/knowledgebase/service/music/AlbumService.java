package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.MusicianDTOTransformer;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
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

    public Album getAlbumById(String albumId) {
        return albumRepository.findById(UUID.fromString(albumId)).orElse(null);
    }

    public Album getAlbumBySlug(String albumSlug) {
        return albumRepository.getAlbumBySlug(albumSlug);
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

    public List<Album> getAllAlbumsSortedByCreatedDesc() {
        return albumRepository.getAllByOrderByCreatedDesc();
    }


    public List<Album> getAllAlbumsByYear(Integer year) {
        return albumRepository.getAllByYear(year);
    }

    public List<Album> getAllAlbumsByMusician(Musician musician) {
        return albumRepository.getAllByMusician(musician);
    }

    public List<Album> getAllAlbumsByGenre(MusicGenre genre) {
        return albumRepository.getAllByMusicGenresIsContaining(genre);
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

    public AlbumViewDTO createAlbum(AlbumCreateEditDTO albumDTO, Musician musician, List<Musician> collaborators) {
        Album album = new Album();
        album.setCreated(Instant.now());
        album.setMusician(musician);
        album.setCollaborators(collaborators);
        setFieldsFromDTO(album, albumDTO);
        album.setSlug(album.getMusician().getSlug() + "-" + SlugFormatter.slugFormatter(album.getSlug()));
        return getAlbumViewDTO(albumRepository.save(album));
    }

    public AlbumViewDTO updateAlbum(String albumSlug, AlbumCreateEditDTO albumDTO, List<Musician> collaborators) {
        Album albumBySlug = getAlbumBySlug(albumSlug);
        albumBySlug.setCollaborators(collaborators);
        setFieldsFromDTO(albumBySlug, albumDTO);
        return getAlbumViewDTO(albumBySlug);
    }

    public void deleteAlbumBySlug(String slug) {
        albumRepository.delete(getAlbumBySlug(slug));
    }

    public List<AlbumViewDTO> getEssentialAlbumsViewDTOList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumViewDTO)
                .filter(albumViewDTO -> albumViewDTO.getEssentialAlbumsRank() != null)
                .sorted(Comparator.comparing(AlbumViewDTO::getEssentialAlbumsRank))
                .collect(Collectors.toList());
    }

    public AlbumViewDTO getAlbumViewDTO(Album album) {
        return AlbumViewDTO.builder()
                .created(InstantFormatter.instantFormatterYMD(album.getCreated()))
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .collaborators(MusicianDTOTransformer.getMusicianSelectDTOList(album.getCollaborators()))
                .feature(album.getFeature())
                .artwork(album.getArtwork())
                .year(album.getYear())
                .musicGenres(album.getMusicGenres())
                .rating(album.getRating())
                .yearEndRank(album.getYearEndRank())
                .essentialAlbumsRank(album.getEssentialAlbumsRank())
                .highlights(album.getHighlights())
                .description(album.getDescription())
                .build();
    }

    public List<AlbumViewDTO> getAlbumViewDTOList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumViewDTO).collect(Collectors.toList());

    }

    public AlbumCreateEditDTO getAlbumCreateEditDTO(Album album) {
        return AlbumCreateEditDTO.builder()
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .collaboratorsUUID(album.getCollaborators().stream().map(Musician::getId).collect(Collectors.toList()))
                .feature(album.getFeature())
                .artwork(album.getArtwork())
                .year(album.getYear())
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
        return albumList.stream().map(this::getAlbumSelectDTO).collect(Collectors.toList());
    }

    public List<AlbumViewDTO> getSortedAlbumViewDTOList(List<Album> albumList, SortType sortType) {
        return getAlbumViewDTOList(albumList.stream()
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
                .collect(Collectors.toList()));
    }

    private void setFieldsFromDTO(Album album, AlbumCreateEditDTO albumDTO) {
        album.setSlug(albumDTO.getSlug().trim());
        album.setTitle(albumDTO.getTitle().trim());
        album.setCatalogNumber(albumDTO.getCatalogNumber());
        album.setFeature(albumDTO.getFeature().trim());
        album.setYear(albumDTO.getYear());

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

    public List<Album> getLatestUpdate() {
        return albumRepository.findFirst20ByOrderByCreatedDesc();
    }

}
