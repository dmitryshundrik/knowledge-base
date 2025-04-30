package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDetailedDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianActivityDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianViewDto;
import com.dmitryshundrik.knowledgebase.mapper.music.MusicianMapper;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import com.dmitryshundrik.knowledgebase.service.core.PersonEventService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.AbstractMap;
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

    public Musician getBySlug(String musicianSlug) {
        return musicianRepository.findBySlug(musicianSlug);
    }

    public Musician getById(UUID musicianID) {
        return musicianRepository.findById(musicianID).orElse(null);
    }

    public Musician getByNickname(String nickName) {
        Musician musicianByNickName = musicianRepository.findByNickNameIgnoreCase(nickName);
        if (musicianByNickName == null) {
            musicianByNickName = musicianRepository.findByNickNameEnIgnoreCase(nickName);
        }
        return musicianByNickName;
    }

    public List<Musician> getAll() {
        return musicianRepository.findAllByOrderByCreated();
    }

    public List<Musician> getAllByUUIDList(List<UUID> uuidList) {
        List<Musician> musicianList = new ArrayList<>();
        for (UUID uuid : uuidList) {
            musicianList.add(getById(uuid));
        }
        return musicianList;
    }

    public List<Musician> getAllByPeriod(MusicPeriod period) {
        return musicianRepository.findAllByMusicPeriodsIsContainingAndCompositionsNotEmpty(period);
    }

    public List<MusicianAllPageResponseDto> getMusicianAllPageResponseDtoOrderByBornAndFounded() {
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

    public List<Musician> getAllWithWorksByYear(Integer year) {
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

    public List<Musician> getTop10MusiciansByPeriod(List<Musician> musicianList) {
        return musicianList.stream()
                .map(musician -> {
                    List<Composition> compositions = compositionService
                            .getAllByMusicianWithRating(musician.getSlug());
                    double totalScore = compositions.stream()
                            .mapToDouble(composition -> composition.getRating() != null ? composition.getRating() : 0).sum();
                    double average = totalScore / compositions.size() + compositions.size() / 10.0;

                    average += compositions.stream().mapToDouble(composition -> {
                                Double rating = composition.getRating();
                                if (rating == null) return 0;
                                if (rating == 5 || rating == 5.5) return 0.1;
                                if (rating == 6 || rating == 6.5) return 0.2;
                                if (rating == 7 || rating == 7.5) return 0.3;
                                if (rating == 8 || rating == 8.5) return 0.4;
                                if (rating == 9 || rating == 9.5) return 0.5;
                                if (rating == 10) return 1.0;
                                return 0;
                            }).sum();

                    return new AbstractMap.SimpleEntry<>(musician, average);
                }).sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // Сортировка по убыванию
                .limit(10) // Ограничение до топ-10
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<MusicianAllPageResponseDto> getMusicianAllPageResponseDto() {
        return musicianRepository.getAllMusicianAllPageResponseDto();
    }

    public List<MusicianArchiveDto> getAllMusicianManagementResponseDto() {
        return musicianRepository.getAllMusicianManagementDto();
    }

    public MusicianArchiveDetailedDto getMusicianManagementDetailedResponseDto(Musician musician) {
        MusicianArchiveDetailedDto musicianDto = musicianMapper.toMusicianManagementDetailedResponseDto(musician);
        musicianDto.setMusicGenres(getSortedMusicGenresByMusician(musician));
        musicianDto.setCreated(InstantFormatter.instantFormatterYMDHMS(musician.getCreated()));
        return musicianDto;
    }

    public List<MusicianArchiveDetailedDto> getAllMusicianManagementDetailedResponseDto() {
        List<Musician> musicianList = musicianRepository.findAllByOrderByCreatedDesc();
        return musicianList.stream().map(this::getMusicianManagementDetailedResponseDto).toList();
    }

    public List<MusicianActivityDto> getLatestUpdate() {
        return musicianRepository.findFirst20ByOrderByCreatedDesc();
    }

    public MusicianViewDto createMusician(MusicianCreateEditDto musicianDto) {
        Musician musician = new Musician();
        setFieldsFromDto(musician, musicianDto);
        musician.setSlug(SlugFormatter.slugFormatter(musician.getSlug()));
        return getMusicianViewDto(musicianRepository.save(musician));
    }

    public MusicianViewDto updateMusician(String musicianSlug, MusicianCreateEditDto musicianDto) {
        Musician musicianBySlug = getBySlug(musicianSlug);
        setFieldsFromDto(musicianBySlug, musicianDto);
        return getMusicianViewDto(musicianBySlug);
    }

    public void deleteMusician(String musicianSlug) {
        musicianRepository.deleteBySlug(musicianSlug);
    }

    public void updateMusicianImage(String musicianSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Musician musicianBySlug = getBySlug(musicianSlug);
            musicianBySlug.setImage(new String(bytes));
        }
    }

    public void deleteMusicianImage(String musicianSlug) {
        Musician musicianBySlug = getBySlug(musicianSlug);
        musicianBySlug.setImage(null);
    }

    public MusicianViewDto getMusicianViewDto(Musician musician) {
        return MusicianViewDto.builder()
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
                        .getPersonEventDtoList(musician.getEvents()) : null)
                .albums(musician.getAlbums() != null ? albumService
                        .getSortedAlbumViewDtoList(musician.getAlbums(), musician.getAlbumsSortType()) : null)
                .collaborations(musician.getCollaborations() != null ? albumService
                        .getAlbumViewDtoList(musician.getCollaborations().stream()
                                .sorted(Comparator.comparing(Album::getYear))
                                .collect(Collectors.toList())) : null)
                .essentialAlbums(musician.getAlbums() != null ? albumService
                        .getEssentialAlbumsViewDtoList(musician.getAlbums()) : null)
                .compositions(compositionService
                        .getCompositionViewDtoList(compositionService
                                .getMusicianCompositionsOrderByFacade(musician.getId(), musician.getCompositionsSortType())))
                .essentialCompositions(musician.getCompositions() != null ? compositionService
                        .getEssentialCompositionsViewDtoList(musician.getCompositions()) : null)
                .build();
    }

    public MusicianCreateEditDto getMusicianCreateEditDto(Musician musician) {
        return MusicianCreateEditDto.builder()
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
                .events(personEventService.getPersonEventDtoList(musician.getEvents()))
                .albums(albumService.getSortedAlbumViewDtoList(musician.getAlbums(), musician.getAlbumsSortType()))
                .compositions(compositionService
                        .getCompositionViewDtoList(compositionService
                                .getMusicianCompositionsOrderByFacade(musician.getId(), musician.getCompositionsSortType())))
                .dateNotification(musician.getDateNotification())
                .build();
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

    public void setFieldsToCompositionDto(String musicianSlug, CompositionCreateEditDto compositionDto) {
        Musician musicianBySlug = getBySlug(musicianSlug);
        compositionDto.setMusicianNickname(musicianBySlug.getNickName());
        compositionDto.setMusicianSlug(musicianBySlug.getSlug());
    }

    public void setFieldsToAlbumDto(String musicianSlug, AlbumCreateEditDto albumDto) {
        Musician musicianBySlug = getBySlug(musicianSlug);
        albumDto.setMusicianNickname(musicianBySlug.getNickName());
        albumDto.setMusicianSlug(musicianBySlug.getSlug());
    }

    private void setFieldsFromDto(Musician musician, MusicianCreateEditDto musicianDto) {
        musician.setSlug(musicianDto.getSlug().trim());
        musician.setFirstName(musicianDto.getFirstName());
        musician.setLastName(musicianDto.getLastName());
        musician.setNickName(musicianDto.getNickName());
        musician.setNickNameEn(musicianDto.getNickNameEn());
        musician.setGender(musicianDto.getGender());
        musician.setBorn(musicianDto.getBorn());
        musician.setDied(musicianDto.getDied());
        musician.setFounded(musicianDto.getFounded());
        musician.setBirthDate(musicianDto.getBirthDate());
        musician.setDeathDate(musicianDto.getDeathDate());
        musician.setBirthplace(musicianDto.getBirthplace());
        musician.setBased(musicianDto.getBased());
        musician.setOccupation(musicianDto.getOccupation());
        musician.setCatalogTitle(musicianDto.getCatalogTitle());
        musician.setMusicPeriods(musicianDto.getMusicPeriods());
        musician.setAlbumsSortType(musicianDto.getAlbumsSortType());
        musician.setCompositionsSortType(musicianDto.getCompositionsSortType());
        musician.setSpotifyLink(musicianDto.getSpotifyLink());
        musician.setDateNotification(musicianDto.getDateNotification());
    }

    public Set<Musician> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Musician> musicianBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianBirthList.addAll(musicianRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i), null));
        }
        return musicianBirthList;
    }

    public Set<Musician> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Musician> musicianDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianDeathList.addAll(musicianRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i), null));
        }
        return musicianDeathList;
    }

    public Set<Musician> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Musician> musicianBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianBirthList.addAll(musicianRepository
                    .findAllWithCurrentBirth(LocalDate.now().plusDays(i), true));
        }
        return musicianBirthList;
    }

    public Set<Musician> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Musician> musicianDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianDeathList.addAll(musicianRepository
                    .findAllWithCurrentDeath(LocalDate.now().plusDays(i), true));
        }
        return musicianDeathList;
    }

    public String isSlugExists(String musicianSlug) {
        String message = "";
        if (getBySlug(musicianSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public Long getRepositorySize() {
        return musicianRepository.getSize();
    }
}
