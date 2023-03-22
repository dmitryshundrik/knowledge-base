package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.*;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import com.dmitryshundrik.knowledgebase.service.common.EventService;
import com.dmitryshundrik.knowledgebase.util.Constants;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicianService {

    @Autowired
    private MusicianRepository musicianRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    public Musician getMusicianById(UUID id) {
        return musicianRepository.findById(id).orElse(null);
    }

    public Musician getMusicianBySlug(String slug) {
        return musicianRepository.getMusicianBySlug(slug);
    }

    public List<Musician> getAllMusicians() {
        return musicianRepository.getAllByOrderByCreated();
    }

    public List<Musician> getAllMusiciansSortedByBorn() {
        return getAllMusicians().stream()
                .sorted((o1, o2) -> {
                    if (o1.getBorn() != null && o2.getBorn() != null) {
                        return o1.getBorn().compareTo(o2.getBorn());
                    }
                    return -1;
                })
                .collect(Collectors.toList());
    }

    public List<Musician> getAllMusiciansWithWorksByYear(Integer year) {
        return musicianRepository.findAll().stream()
                .filter(musician -> {
                    List<Album> albums = musician.getAlbums();
                    for (Album album : albums) {
                        if (Objects.equals(album.getYear(), year)) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    public List<Musician> getAllMusiciansByPeriod(MusicPeriod period) {
        return musicianRepository.getAllByMusicPeriodsIsContaining(period);
    }

    public List<Musician> getAllMusiciansByGenre(MusicGenre genre) {
        return musicianRepository.getAllByMusicGenresIsContaining(genre);
    }

    public void createMusicianByMusicianDTO(MusicianCreateEditDTO musicianDTO) {
        Musician musician = new Musician();
        musician.setCreated(Instant.now());
        musician.setImage(Constants.DEFAULT_PLACEHOLDER);
        setFieldsFromDTO(musician, musicianDTO);
        musicianRepository.save(musician);
    }

    public void updateExistingMusician(MusicianCreateEditDTO musicianDTO, String slug) {
        Musician musicianBySlug = getMusicianBySlug(slug);
        setFieldsFromDTO(musicianBySlug, musicianDTO);
    }

    public void updateMusicianImageBySlug(String slug, byte[] bytes) {
        if (bytes.length != 0) {
            Musician musicianBySlug = getMusicianBySlug(slug);
            musicianBySlug.setImage(new String(bytes));
        }
    }

    public void deleteMuscianImage(String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        musicianBySlug.setImage(Constants.DEFAULT_PLACEHOLDER);
    }

    public void deleteMusicianBySlug(String slug) {
        musicianRepository.deleteBySlug(slug);
    }

    public MusicianViewDTO getMusicianViewDTO(Musician musician) {
        return MusicianViewDTO.builder()
                .created(Formatter.instantFormatterYMDHMS(musician.getCreated()))
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .image(musician.getImage())
                .born(musician.getBorn())
                .died(musician.getDied())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .musicPeriods(musician.getMusicPeriods())
                .musicGenres(musician.getMusicGenres())
                .spotifyLink(musician.getSpotifyLink())
                .events(eventService.eventListToEventDTOList(musician.getEvents()))
                .albums(albumService.getSortedAlbumViewDTOList(musician.getAlbums(), musician.getAlbumsSortType()))
                .essentialAlbums(albumService.getEssentialAlbumsViewDTOList(musician.getAlbums()))
                .compositions(compositionService
                        .getSortedCompositionViewDTOList(musician.getCompositions(), musician.getCompositionsSortType()))
                .essentialCompositions(compositionService.getEssentialCompositionsViewDTOList(musician.getCompositions()))
                .build();
    }

    public List<MusicianViewDTO> getMusicianViewDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(musician -> getMusicianViewDTO(musician)).collect(Collectors.toList());
    }

    public MusicianCreateEditDTO getMusicianCreateEditDTO(Musician musician) {
        return MusicianCreateEditDTO.builder()
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .image(musician.getImage())
                .born(musician.getBorn())
                .died(musician.getDied())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .musicPeriods(musician.getMusicPeriods())
                .classicalGenres(musician.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                        .collect(Collectors.toList()))
                .contemporaryGenres(musician.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                        .collect(Collectors.toList()))
                .albumsSortType(musician.getAlbumsSortType())
                .compositionsSortType(musician.getCompositionsSortType())
                .spotifyLink(musician.getSpotifyLink())
                .events(eventService.eventListToEventDTOList(musician.getEvents()))
                .albums(albumService.getSortedAlbumViewDTOList(musician.getAlbums(), musician.getAlbumsSortType()))
                .compositions(compositionService
                        .getSortedCompositionViewDTOList(musician.getCompositions(), musician.getCompositionsSortType()))
                .build();
    }

    public void setMusicianFieldsToAlbumDTO(AlbumCreateEditDTO albumDTO, String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        albumDTO.setMusicianNickname(musicianBySlug.getNickName());
        albumDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    public MusicianSelectDTO getMusicianSelectDTO(Musician musician) {
        return MusicianSelectDTO.builder()
                .id(musician.getId().toString())
                .nickName(musician.getNickName())
                .build();
    }

    public List<MusicianSelectDTO> getMusicianSelectDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(musician -> getMusicianSelectDTO(musician)).collect(Collectors.toList());
    }

    public void setMusicianFieldsToCompositionDTO(CompositionCreateEditDTO compositionDTO, String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        compositionDTO.setMusicianNickname(musicianBySlug.getNickName());
        compositionDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    private void setFieldsFromDTO(Musician musician, MusicianCreateEditDTO musicianDTO) {
        musician.setSlug(musicianDTO.getSlug());
        musician.setFirstName(musicianDTO.getFirstName());
        musician.setLastName(musicianDTO.getLastName());
        musician.setNickName(musicianDTO.getNickName());
        musician.setBorn(musicianDTO.getBorn());
        musician.setDied(musicianDTO.getDied());
        musician.setBirthDate(musicianDTO.getBirthDate());
        musician.setDeathDate(musicianDTO.getDeathDate());
        musician.setBirthplace(musicianDTO.getBirthplace());
        musician.setMusicPeriods(musicianDTO.getMusicPeriods());

        List<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(musicianDTO.getClassicalGenres());
        musicGenres.addAll(musicianDTO.getContemporaryGenres());

        musician.setMusicGenres(musicGenres);
        musician.setAlbumsSortType(musicianDTO.getAlbumsSortType());
        musician.setCompositionsSortType(musicianDTO.getCompositionsSortType());
        musician.setSpotifyLink(musicianDTO.getSpotifyLink());
    }

    public List<MusicianViewDTO> getLatestUpdate() {
        return getMusicianViewDTOList(musicianRepository.findFirst10ByOrderByCreated());
    }
}
