package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.dto.music.MusicianAllPageResponseDto;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianManagementResponseDto;
import com.dmitryshundrik.knowledgebase.entity.music.Album;
import com.dmitryshundrik.knowledgebase.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.dto.music.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianActivityDto;
import com.dmitryshundrik.knowledgebase.dto.music.MusicianViewDTO;
import com.dmitryshundrik.knowledgebase.mapper.music.MusicianMapper;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import com.dmitryshundrik.knowledgebase.service.common.PersonEventService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicianService {

    private final MusicianRepository musicianRepository;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final PersonEventService personEventService;

    private final MusicianMapper musicianMapper;

    public List<Musician> getAll() {
        return musicianRepository.getAllByOrderByCreated();
    }

    public Long getMusicianRepositorySize() {
        return musicianRepository.getSize();
    }

    public Musician getMusicianById(UUID musicianID) {
        return musicianRepository.findById(musicianID).orElse(null);
    }

    public List<Musician> getAllMusiciansByUUIDList(List<UUID> uuidList) {
        List<Musician> musicianList = new ArrayList<>();
        for (UUID uuid : uuidList) {
            musicianList.add(getMusicianById(uuid));
        }
        return musicianList;
    }

    public Musician getMusicianBySlug(String musicianSlug) {
        return musicianRepository.getMusicianBySlug(musicianSlug);
    }

    public Musician getMusicianByNickname(String nickName) {
        Musician musicianByNickName = musicianRepository.getMusicianByNickNameIgnoreCase(nickName);
        if (musicianByNickName == null) {
            musicianByNickName = musicianRepository.getMusicianByNickNameEnIgnoreCase(nickName);
        }
        return musicianByNickName;
    }

    public List<Musician> getAllMusiciansOrderedByCreatedDesc() {
        return musicianRepository.getAllByOrderByCreatedDesc();
    }

    public List<MusicianAllPageResponseDto> getMusicianAllPageResponseDtoSortedByBornAndFounded() {
        return getMusicianAllPageResponseDto().stream()
                .filter(musician -> musician.getBorn() != null || musician.getFounded() != null)
                .sorted((o1, o2) -> {
                    Integer o1Date = o1.getBorn() != null ? o1.getBorn() : o1.getFounded();
                    Integer o2Date = o2.getBorn() != null ? o2.getBorn() : o2.getFounded();
                    if (o2Date != null) {
                        return o1Date.compareTo(o2Date);
                    }
                    return -1;
                }).toList();
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
                }).toList();
    }

    public List<Musician> getAllMusiciansByPeriod(MusicPeriod period) {
        return musicianRepository.getAllByMusicPeriodsIsContainingAndCompositionsNotEmpty(period);
    }

    public List<Musician> getTop10MusiciansByPeriod(List<Musician> musicianList) {
        Map<Musician, Double> map = new HashMap<>();
        for (Musician musician : musicianList) {
            List<Composition> compositions = compositionService.getAllByMusicianWithRating(musician.getSlug());
            double totalScore = 0.0;
            for (Composition composition : compositions) {
                totalScore = totalScore + (composition.getRating() != null ? composition.getRating() : 0);
            }
            double average = totalScore / compositions.size() + compositions.size() / 10.0;
            for (Composition composition : compositions) {
                Double rating = composition.getRating();
                if (rating == 5 || rating == 5.5) {
                    average = average + 0.1;
                } else if (rating == 6 || rating == 6.5) {
                    average = average + 0.2;
                } else if (rating == 7 || rating == 7.5) {
                    average = average + 0.3;
                } else if (rating == 8 || rating == 8.5) {
                    average = average + 0.4;
                } else if (rating == 9 || rating == 9.5) {
                    average = average + 0.5;
                } else if (rating == 10) {
                    average = average + 1;
                }
            }
            map.put(musician, average);
        }
        List<Map.Entry<Musician, Double>> list = new ArrayList<>(map.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return list.stream()
                .map(Map.Entry::getKey)
                .limit(10).collect(Collectors.toList());
    }

    public List<MusicianAllPageResponseDto> getMusicianAllPageResponseDto() {
        return musicianRepository.getAllMusicianAllPageResponseDto();
    }

    public MusicianManagementResponseDto getMusicianManagementResponseDto(Musician musician) {
        MusicianManagementResponseDto musicianManagementResponseDto = musicianMapper
                .toMusicianManagementAllResponseDto(musician);
        musicianManagementResponseDto.setMusicGenres(getSortedMusicGenresByMusician(musician));
        return musicianManagementResponseDto;
    }

    public List<MusicianManagementResponseDto> getAllMusicianManagementResponseDto(List<Musician> musicianList) {
        return musicianList.stream().map(this::getMusicianManagementResponseDto).toList();
    }

    public MusicianViewDTO createMusician(MusicianCreateEditDTO musicianDTO) {
        Musician musician = new Musician();
        setFieldsFromDTO(musician, musicianDTO);
        musician.setSlug(SlugFormatter.slugFormatter(musician.getSlug()));
        return getMusicianViewDTO(musicianRepository.save(musician));
    }

    public MusicianViewDTO updateMusician(String musicianSlug, MusicianCreateEditDTO musicianDTO) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        setFieldsFromDTO(musicianBySlug, musicianDTO);
        return getMusicianViewDTO(musicianBySlug);
    }

    public void updateMusicianImageBySlug(String musicianSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Musician musicianBySlug = getMusicianBySlug(musicianSlug);
            musicianBySlug.setImage(new String(bytes));
        }
    }

    public void deleteMusicianImage(String musicianSlug) {
        Musician musicianBySlug = getMusicianBySlug(musicianSlug);
        musicianBySlug.setImage(null);
    }

    public void deleteMusicianBySlug(String musicianSlug) {
        musicianRepository.deleteBySlug(musicianSlug);
    }

    public MusicianViewDTO getMusicianViewDTO(Musician musician) {
        return MusicianViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(musician.getCreated()))
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .nickNameEn(musician.getNickNameEn())
                .gender(musician.getGender())
                .image(musician.getImage())
                .born(musician.getBorn())
                .died(musician.getDied())
                .founded(musician.getFounded())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .based(musician.getBased())
                .occupation(musician.getOccupation())
                .catalogTitle(musician.getCatalogTitle())
                .musicPeriods(musician.getMusicPeriods())
                .musicGenres(getSortedMusicGenresByMusician(musician))
                .spotifyLink(musician.getSpotifyLink())
                .events(musician.getEvents() != null ? personEventService
                        .getPersonEventDTOList(musician.getEvents()) : null)
                .albums(musician.getAlbums() != null ? albumService
                        .getSortedAlbumViewDTOList(musician.getAlbums(), musician.getAlbumsSortType()) : null)
                .collaborations(musician.getCollaborations() != null ? albumService
                        .getAlbumViewDTOList(musician.getCollaborations().stream()
                                .sorted(Comparator.comparing(Album::getYear))
                                .collect(Collectors.toList())) : null)
                .essentialAlbums(musician.getAlbums() != null ? albumService
                        .getEssentialAlbumsViewDTOList(musician.getAlbums()) : null)
                .compositions(compositionService
                        .getCompositionViewDTOList(compositionService
                                .getMusicianCompositionsOrderByFacade(musician.getId(), musician.getCompositionsSortType())))
                .essentialCompositions(musician.getCompositions() != null ? compositionService
                        .getEssentialCompositionsViewDTOList(musician.getCompositions()) : null)
                .build();
    }

    public List<MusicianViewDTO> getMusicianViewDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(this::getMusicianViewDTO).collect(Collectors.toList());
    }

    public List<MusicGenre> getSortedMusicGenresByMusician(Musician musician) {
        Map<MusicGenre, Integer> map = new HashMap<>();
        List<Album> albums = musician.getAlbums();
        List<Composition> compositions = musician.getCompositions();
        if (albums != null) {
            for (Album album : musician.getAlbums()) {
                for (MusicGenre musicGenre : album.getMusicGenres()) {
                    map.merge(musicGenre, 1, Integer::sum);
                }
            }
        }
        if (albums != null && albums.isEmpty() && compositions != null) {
            for (Composition composition : musician.getCompositions()) {
                for (MusicGenre musicGenre : composition.getMusicGenres()) {
                    map.merge(musicGenre, 1, Integer::sum);
                }
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
                .nickNameEn(musician.getNickNameEn())
                .gender(musician.getGender())
                .image(musician.getImage())
                .born(musician.getBorn())
                .died(musician.getDied())
                .founded(musician.getFounded())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .birthplace(musician.getBirthplace())
                .based(musician.getBased())
                .occupation(musician.getOccupation())
                .catalogTitle(musician.getCatalogTitle())
                .musicPeriods(musician.getMusicPeriods())
                .albumsSortType(musician.getAlbumsSortType())
                .compositionsSortType(musician.getCompositionsSortType())
                .spotifyLink(musician.getSpotifyLink())
                .events(personEventService.getPersonEventDTOList(musician.getEvents()))
                .albums(albumService.getSortedAlbumViewDTOList(musician.getAlbums(), musician.getAlbumsSortType()))
                .compositions(compositionService
                        .getCompositionViewDTOList(compositionService
                                        .getMusicianCompositionsOrderByFacade(musician.getId(), musician.getCompositionsSortType())))
                .build();
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
        musician.setNickNameEn(musicianDTO.getNickNameEn());
        musician.setGender(musicianDTO.getGender());
        musician.setBorn(musicianDTO.getBorn());
        musician.setDied(musicianDTO.getDied());
        musician.setFounded(musicianDTO.getFounded());
        musician.setBirthDate(musicianDTO.getBirthDate());
        musician.setDeathDate(musicianDTO.getDeathDate());
        musician.setBirthplace(musicianDTO.getBirthplace());
        musician.setBased(musicianDTO.getBased());
        musician.setOccupation(musicianDTO.getOccupation());
        musician.setCatalogTitle(musicianDTO.getCatalogTitle());
        musician.setMusicPeriods(musicianDTO.getMusicPeriods());
        musician.setAlbumsSortType(musicianDTO.getAlbumsSortType());
        musician.setCompositionsSortType(musicianDTO.getCompositionsSortType());
        musician.setSpotifyLink(musicianDTO.getSpotifyLink());
    }

    public String musicianSlugIsExist(String musicianSlug) {
        String message = "";
        if (getMusicianBySlug(musicianSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<MusicianActivityDto> getLatestUpdate() {
        return musicianRepository.findFirst20ByOrderByCreatedDesc();
    }

    public Set<Musician> getAllWithCurrentBirth() {
        Set<Musician> musicianBirthList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            musicianBirthList.addAll(musicianRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i)));
        }
        return musicianBirthList;
    }

    public Set<Musician> getAllWithCurrentDeath() {
        Set<Musician> musicianDeathList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            musicianDeathList.addAll(musicianRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i)));
        }
        return musicianDeathList;
    }
}
