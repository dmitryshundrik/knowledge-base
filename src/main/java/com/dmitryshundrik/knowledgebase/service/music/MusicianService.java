package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.*;
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
                    Integer o1Date = o1.getBorn() != null ? o1.getBorn() : o1.getFounded();
                    Integer o2Date = o2.getBorn() != null ? o2.getBorn() : o2.getFounded();
                    if (o1Date != null && o2Date != null) {
                        return o1Date.compareTo(o2Date);
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



    public List<Musician> getBestMusicianByPeriod(MusicPeriod period) {
        List<Musician> allMusiciansByPeriod = getAllMusiciansByPeriod(period);
        Map<Musician, Double> map = new HashMap<>();
        for (Musician musician : allMusiciansByPeriod) {
            List<Composition> compositions = musician.getCompositions();
            double totalScore = 0.0;
            for (Composition composition : compositions) {
                totalScore = totalScore + (composition.getRating() != null ? composition.getRating() : 0);
            }
            double average = totalScore / compositions.size();
            for (Composition composition : compositions) {
                Double rating = composition.getRating();
                if (rating == 5 || rating == 5.5) {
                    average = average + 1;
                } else if (rating == 6 || rating == 6.5) {
                    average = average + 2;
                } else if (rating == 7 || rating == 7.5) {
                    average = average + 3;
                } else if (rating == 8 || rating == 8.5) {
                    average = average + 4;
                } else if (rating == 9 || rating == 9.5) {
                    average = average + 5;
                }
            }
            map.put(musician, average);
        }
        List<Map.Entry<Musician, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list.stream()
                .map(Map.Entry::getKey)
                .limit(5).collect(Collectors.toList());
    }

    public void createMusicianByDTO(MusicianCreateEditDTO musicianDTO) {
        Musician musician = new Musician();
        musician.setCreated(Instant.now());
        musician.setImage(Constants.DEFAULT_PLACEHOLDER);
        setFieldsFromDTO(musician, musicianDTO);
        musicianRepository.save(musician);
    }

    public void updateMusician(String musicianSlug, MusicianCreateEditDTO musicianDTO) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        setFieldsFromDTO(musicianBySlug, musicianDTO);
    }

    public void updateMusicianImageBySlug(String musicianSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Musician musicianBySlug = getMusicianBySlug(musicianSlug);
            musicianBySlug.setImage(new String(bytes));
        }
    }

    public void deleteMuscianImage(String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        musicianBySlug.setImage(Constants.DEFAULT_PLACEHOLDER);
    }

    public void deleteMusicianBySlug(String musicianSlug) {
        musicianRepository.deleteBySlug(musicianSlug);
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
                .founded(musician.getFounded())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .occupation(musician.getOccupation())
                .musicPeriods(musician.getMusicPeriods())
                .musicGenres(getSortedMusicGenresByMusisian(musician))
                .spotifyLink(musician.getSpotifyLink())
                .events(eventService.getEventDTOList(musician.getEvents()))
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

    public List<MusicGenre> getSortedMusicGenresByMusisian(Musician musician) {
        Map<MusicGenre, Integer> map = new HashMap<>();
        for (Album album : musician.getAlbums()) {
            for (MusicGenre musicGenre : album.getMusicGenres()) {
                map.merge(musicGenre, 1, Integer::sum);
            }
        }
        for (Composition composition : musician.getCompositions()) {
            for (MusicGenre musicGenre : composition.getMusicGenres()) {
                map.merge(musicGenre, 1, Integer::sum);
            }
        }
        List<Map.Entry<MusicGenre, Integer>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list.stream()
                .map(Map.Entry::getKey)
                .limit(10).collect(Collectors.toList());
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
                .founded(musician.getFounded())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .occupation(musician.getOccupation())
                .musicPeriods(musician.getMusicPeriods())
                .albumsSortType(musician.getAlbumsSortType())
                .compositionsSortType(musician.getCompositionsSortType())
                .spotifyLink(musician.getSpotifyLink())
                .events(eventService.getEventDTOList(musician.getEvents()))
                .albums(albumService.getSortedAlbumViewDTOList(musician.getAlbums(), musician.getAlbumsSortType()))
                .compositions(compositionService
                        .getSortedCompositionViewDTOList(musician.getCompositions(), musician.getCompositionsSortType()))
                .build();
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

    public void setFieldsToCompositionDTO(String musicianSlug, CompositionCreateEditDTO compositionDTO) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        compositionDTO.setMusicianNickname(musicianBySlug.getNickName());
        compositionDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    public void setFieldsToAlbumDTO(String musicianSlug, AlbumCreateEditDTO albumDTO) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        albumDTO.setMusicianNickname(musicianBySlug.getNickName());
        albumDTO.setMusicianSlug(musicianBySlug.getSlug());
    }

    private void setFieldsFromDTO(Musician musician, MusicianCreateEditDTO musicianDTO) {
        musician.setSlug(musicianDTO.getSlug().trim());
        musician.setFirstName(musicianDTO.getFirstName());
        musician.setLastName(musicianDTO.getLastName());
        musician.setNickName(musicianDTO.getNickName());
        musician.setBorn(musicianDTO.getBorn());
        musician.setDied(musicianDTO.getDied());
        musician.setFounded(musicianDTO.getFounded());
        musician.setBirthDate(musicianDTO.getBirthDate());
        musician.setDeathDate(musicianDTO.getDeathDate());
        musician.setBirthplace(musicianDTO.getBirthplace());
        musician.setOccupation(musicianDTO.getOccupation());
        musician.setMusicPeriods(musicianDTO.getMusicPeriods());
        musician.setAlbumsSortType(musicianDTO.getAlbumsSortType());
        musician.setCompositionsSortType(musicianDTO.getCompositionsSortType());
        musician.setSpotifyLink(musicianDTO.getSpotifyLink());
    }

    public List<MusicianViewDTO> getLatestUpdate() {
        return getMusicianViewDTOList(musicianRepository.findFirst10ByOrderByCreated());
    }
}
