package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.MusicianDtoTransformer;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public static final String DECADE_2010s = "2010";

    public static final String DECADE_2020s = "2020";

    public Album getById(String albumId) {
        return albumRepository.findById(UUID.fromString(albumId)).orElse(null);
    }

    public Album getBySlug(String albumSlug) {
        return albumRepository.findAlbumBySlug(albumSlug);
    }

    public List<Album> getAll() {
        return albumRepository.findAll();
    }

    public List<Album> getAllOrderByCreatedDesc() {
        return albumRepository.findAllByOrderByCreatedDesc();
    }

    public List<Album> getAllByYear(Integer year) {
        return albumRepository.findAllByYear(year);
    }

    public List<Album> getAllByDecade(String decade) {
        List<Album> albumsByDecade = new ArrayList<>();
        if (DECADE_2010s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllByDecadesOrderByRatingDesc(2009, 2020));
        } else if (DECADE_2020s.equals(decade)) {
            albumsByDecade.addAll(albumRepository.findAllByDecadesOrderByRatingDesc(2019, 2030));
        }
        return albumsByDecade;
    }

    public List<Album> getAllByMusician(Musician musician) {
        return albumRepository.findAllByMusician(musician);
    }

    public List<Album> getAllByGenre(MusicGenre genre) {
        return albumRepository.findAllByMusicGenresIsContaining(genre);
    }

    public List<AlbumViewDto> get10BestAlbumsByYear(Integer year) {
        return getAlbumViewDtoList(albumRepository.findAllByYear(year).stream()
                .sorted((o1, o2) -> o2.getRating().compareTo(o1.getRating()))
                .limit(10)
                .collect(Collectors.toList()));
    }

    public List<AlbumViewDto> getTop100BestAlbums() {
        return getAlbumViewDtoList(albumRepository.findFirst100ByRatingIsNotNullOrderByRatingDesc());
    }

    public List<Integer> getAllYearsFromAlbums() {
        return albumRepository.findAllYearsFromAlbums();
    }

    public AlbumViewDto createAlbum(AlbumCreateEditDto albumDto, Musician musician, List<Musician> collaborators) {
        Album album = new Album();
        album.setMusician(musician);
        album.setCollaborators(collaborators);
        setFieldsFromDto(album, albumDto);
        album.setSlug(musician.getSlug() + "-" + SlugFormatter.slugFormatter(album.getSlug()));
        return getAlbumViewDto(albumRepository.save(album));
    }

    public AlbumViewDto updateAlbum(String albumSlug, AlbumCreateEditDto albumDto, List<Musician> collaborators) {
        Album albumBySlug = getBySlug(albumSlug);
        albumBySlug.setCollaborators(collaborators);
        setFieldsFromDto(albumBySlug, albumDto);
        return getAlbumViewDto(albumBySlug);
    }

    public void deleteAlbum(String slug) {
        albumRepository.delete(getBySlug(slug));
    }

    public List<AlbumViewDto> getEssentialAlbumsViewDtoList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumViewDto)
                .filter(albumViewDto -> albumViewDto.getEssentialAlbumsRank() != null)
                .sorted(Comparator.comparing(AlbumViewDto::getEssentialAlbumsRank))
                .collect(Collectors.toList());
    }

    public AlbumViewDto getAlbumViewDto(Album album) {
        return AlbumViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(album.getCreated()))
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .collaborators(MusicianDtoTransformer.getMusicianSelectDtoList(album.getCollaborators()))
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

    public List<AlbumViewDto> getAlbumViewDtoList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumViewDto).collect(Collectors.toList());

    }

    public AlbumCreateEditDto getAlbumCreateEditDto(Album album) {
        return AlbumCreateEditDto.builder()
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

    public AlbumSelectDto getAlbumSelectDto(Album album) {
        return AlbumSelectDto.builder()
                .id(album.getId().toString())
                .title(album.getTitle())
                .build();
    }

    public List<AlbumSelectDto> getAlbumSelectDtoList(List<Album> albumList) {
        return albumList.stream().map(this::getAlbumSelectDto).collect(Collectors.toList());
    }

    public List<AlbumViewDto> getSortedAlbumViewDtoList(List<Album> albumList, SortType sortType) {
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
                .collect(Collectors.toList()));
    }

    private void setFieldsFromDto(Album album, AlbumCreateEditDto albumDto) {
        album.setSlug(albumDto.getSlug().trim());
        album.setTitle(albumDto.getTitle().trim());
        album.setCatalogNumber(albumDto.getCatalogNumber());
        album.setFeature(albumDto.getFeature());
        album.setYear(albumDto.getYear());

        List<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(albumDto.getClassicalGenres());
        musicGenres.addAll(albumDto.getContemporaryGenres());

        album.setMusicGenres(musicGenres);
        album.setRating(albumDto.getRating());
        album.setYearEndRank(albumDto.getYearEndRank());
        album.setEssentialAlbumsRank(albumDto.getEssentialAlbumsRank());
        album.setHighlights(albumDto.getHighlights());
        album.setDescription(albumDto.getDescription());
    }

    public List<Album> getLatestUpdate() {
        return albumRepository.findFirst20ByOrderByCreatedDesc();
    }

    public Long getRepositorySize() {
        return albumRepository.getSize();
    }
}
