package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.*;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import com.dmitryshundrik.knowledgebase.service.EventService;
import com.dmitryshundrik.knowledgebase.util.Constants;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
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

    public List<Musician> getAllMusicians() {
        return musicianRepository.getAllByOrderByCreated();
    }

    public Musician getMusicianBySlug(String slug) {
        return musicianRepository.getMusicianBySlug(slug);
    }

    public void createMusicianByMusicianDTO(MusicianCreateEditDTO musicianCreateEditDTO) {
        Musician musician = new Musician();
        musician.setCreated(Instant.now());
        musician.setImage(Constants.DEFAULT_PLACEHOLDER);
        setMusicianFieldsFromDTO(musician, musicianCreateEditDTO);
        musicianRepository.save(musician);
    }

    public void updateExistingMusician(MusicianCreateEditDTO musicianCreateEditDTO, String slug) {
        Musician musicianBySlug = getMusicianBySlug(slug);
        setMusicianFieldsFromDTO(musicianBySlug, musicianCreateEditDTO);
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
                .created(Formatter.instantFormatter(musician.getCreated()))
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

    public void setMusicianFieldsToAlbumDTO(AlbumCreateEditDTO albumCreateEditDTO, String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        albumCreateEditDTO.setMusicianNickname(musicianBySlug.getNickName());
        albumCreateEditDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    public void setMusicianFieldsToCompositionDTO(CompositionCreateEditDTO compositionCreateEditDTO, String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        compositionCreateEditDTO.setMusicianNickname(musicianBySlug.getNickName());
        compositionCreateEditDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    private void setMusicianFieldsFromDTO(Musician musician, MusicianCreateEditDTO musicianCreateEditDTO) {
        musician.setSlug(musicianCreateEditDTO.getSlug());
        musician.setFirstName(musicianCreateEditDTO.getFirstName());
        musician.setLastName(musicianCreateEditDTO.getLastName());
        musician.setNickName(musicianCreateEditDTO.getNickName());
        musician.setBorn(musicianCreateEditDTO.getBorn());
        musician.setDied(musicianCreateEditDTO.getDied());
        musician.setBirthDate(musicianCreateEditDTO.getBirthDate());
        musician.setDeathDate(musicianCreateEditDTO.getDeathDate());
        musician.setBirthplace(musicianCreateEditDTO.getBirthplace());
        musician.setMusicPeriods(musicianCreateEditDTO.getMusicPeriods());
        musician.setMusicGenres(musicianCreateEditDTO.getClassicalGenres());
        musician.getMusicGenres().addAll(musicianCreateEditDTO.getContemporaryGenres());
        musician.setAlbumsSortType(musicianCreateEditDTO.getAlbumsSortType());
        musician.setCompositionsSortType(musicianCreateEditDTO.getCompositionsSortType());
        musician.setSpotifyLink(musicianCreateEditDTO.getSpotifyLink());
    }
}
