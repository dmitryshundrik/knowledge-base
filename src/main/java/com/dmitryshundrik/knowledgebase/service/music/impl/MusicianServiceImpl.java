package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDetailedDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
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
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class MusicianServiceImpl implements MusicianService {

    private final MusicianRepository musicianRepository;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    private final MusicGenreService musicGenreService;

    private final PersonEventService personEventService;

    private final MusicianMapper musicianMapper;

    @Override
    public Musician getBySlug(String musicianSlug) {
        return musicianRepository.findBySlug(musicianSlug);
    }

    @Override
    public Musician getById(UUID musicianId) {
        return musicianRepository.findById(musicianId).orElse(null);
    }

    @Override
    public Musician getByNickname(String nickName) {
        Musician musicianByNickName = musicianRepository.findByNickNameIgnoreCase(nickName);
        if (musicianByNickName == null) {
            musicianByNickName = musicianRepository.findByNickNameEnIgnoreCase(nickName);
        }
        return musicianByNickName;
    }

    @Override
    public MusicianArchiveDetailedDto getMusicianArchiveDetailedDto(Musician musician) {
        MusicianArchiveDetailedDto musicianDto = musicianMapper.toMusicianArchiveDetailedDto(musician);
        musicianDto.setCreated(InstantFormatter.instantFormatterYMDHMS(musician.getCreated()));
        musicianDto.setMusicGenres(musicGenreService.getAllByMusicianOrderByCount(musician));
        return musicianDto;
    }

    @Override
    public List<Musician> getAllByUUIDList(List<UUID> uuidList) {
        List<Musician> musicianList = new ArrayList<>();
        for (UUID uuid : uuidList) {
            musicianList.add(getById(uuid));
        }
        return musicianList;
    }

    @Override
    public List<Musician> getAllByPeriod(MusicPeriod period) {
        return musicianRepository.findAllByMusicPeriodsIsContainingAndCompositionsNotEmpty(period);
    }

    @Override
    public List<Musician> getAllBestByPeriod(MusicPeriod period) {
        List<Musician> musicianList = getAllByPeriod(period);
        Map<Musician, List<Composition>> compositionsByMusician = compositionService
                .getAllByMusiciansIn(musicianList);
        record MusicianScore(Musician musician, double score) {
        }
        return musicianList.parallelStream()
                .map(musician -> {
                    List<Composition> compositions = compositionsByMusician.getOrDefault(musician, List.of());
                    double score = calculateMusicianScore(compositions);
                    return new MusicianScore(musician, score);
                })
                .sorted(Comparator.comparingDouble(MusicianScore::score).reversed())
                .map(MusicianScore::musician)
                .limit(10)
                .toList();
    }

    private double calculateMusicianScore(List<Composition> compositions) {
        if (compositions.isEmpty()) {
            return 0;
        }
        double totalScore = compositions.stream()
                .mapToDouble(composition -> composition.getRating() != null ? composition.getRating() : 0)
                .sum();
        double average = totalScore / compositions.size() + compositions.size() / 10.0;

        double bonus = compositions.stream()
                .mapToDouble(composition -> {
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

        return average + bonus;
    }

    @Override
    public List<MusicianSimpleDto> getAllMusicianSimpleDto() {
        return musicianRepository.findAllMusicianSimpleDtoOrderByBornAndFounded();
    }

    public List<MusicianSelectDto> getAllMusicianSelectDto() {
        return musicianRepository.findAllMusicianSelectDtoOrderByNickName();
    }

    @Override
    public List<MusicianSelectDto> getAllActiveMusicianSelectDtoByYear(Integer year) {
        return musicianRepository.findAllActiveMusicianSelectDtoByYearOrderByNickName(year);
    }

    @Override
    public List<MusicianArchiveDto> getAllMusicianArchiveDto() {
        return musicianRepository.findAllMusicianArchiveDto();
    }

    @Override
    public List<MusicianArchiveDetailedDto> getAllMusicianArchiveDetailedDto() {
        List<Musician> musicianList = musicianRepository.findAllByOrderByCreatedDesc();
        return musicianList.stream().map(this::getMusicianArchiveDetailedDto).collect(Collectors.toList());
    }

    @Override
    public List<MusicianActivityDto> getLatestUpdate() {
        return musicianRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public Musician createMusician(MusicianCreateEditDto musicianDto) {
        Musician musician = new Musician();
        setFieldsFromDto(musician, musicianDto);
        musician.setSlug(SlugFormatter.slugFormatter(musician.getSlug()));
        return musicianRepository.save(musician);
    }

    @Override
    public Musician updateMusician(String musicianSlug, MusicianCreateEditDto musicianDto) {
        Musician musician = getBySlug(musicianSlug);
        setFieldsFromDto(musician, musicianDto);
        return musician;
    }

    @Override
    public void deleteMusician(String musicianSlug) {
        musicianRepository.deleteBySlug(musicianSlug);
    }

    @Override
    public void updateMusicianImage(String musicianSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Musician musicianBySlug = getBySlug(musicianSlug);
            musicianBySlug.setImage(new String(bytes));
        }
    }

    @Override
    public void deleteMusicianImage(String musicianSlug) {
        Musician musicianBySlug = getBySlug(musicianSlug);
        musicianBySlug.setImage(null);
    }

    @Override
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
                .musicPeriods(musician.getMusicPeriods().stream()
                        .sorted(Comparator.comparing(MusicPeriod::getApproximateStart))
                        .collect(Collectors.toList()))
                .musicGenres(musicGenreService.getAllByMusicianOrderByCount(musician))
                .spotifyLink(musician.getSpotifyLink())
                .events(musician.getEvents() != null ? personEventService
                        .getPersonEventDtoList(musician.getEvents()) : null)
                .albums(musician.getAlbums() != null ? albumService
                        .getAlbumViewDtoListOrderBy(musician.getAlbums(), musician.getAlbumsSortType()) : null)
                .collaborations(musician.getCollaborations() != null ? albumService
                        .getAlbumViewDtoList(musician.getCollaborations().stream()
                                .sorted(Comparator.comparing(Album::getYear))
                                .collect(Collectors.toList())) : null)
                .essentialAlbums(musician.getAlbums() != null ? albumService
                        .getEssentialAlbumViewDtoList(musician.getAlbums()) : null)
                .compositions(compositionService
                        .getCompositionViewDtoList(compositionService
                                .getAllByMusicianOrderBy(musician.getId(), musician.getCompositionsSortType())))
                .essentialCompositions(musician.getCompositions() != null ? compositionService
                        .getEssentialCompositionsViewDtoList(musician.getCompositions()) : null)
                .build();
    }

    @Override
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
                .albums(albumService.getAlbumViewDtoListOrderBy(musician.getAlbums(), musician.getAlbumsSortType()))
                .compositions(compositionService
                        .getCompositionViewDtoList(compositionService
                                .getAllByMusicianOrderBy(musician.getId(), musician.getCompositionsSortType())))
                .dateNotification(musician.getDateNotification())
                .build();
    }

    public void setFieldsToAlbumDto(String musicianSlug, AlbumCreateEditDto albumDto) {
        MusicianSimpleDto musicianDto = musicianRepository.findMusicianSimpleDtoBySlug(musicianSlug);
        albumDto.setMusicianNickname(musicianDto.nickName());
        albumDto.setMusicianSlug(musicianDto.slug());
    }

    public void setFieldsToCompositionDto(String musicianSlug, CompositionCreateEditDto compositionDto) {
        MusicianSimpleDto musicianDto = musicianRepository.findMusicianSimpleDtoBySlug(musicianSlug);
        compositionDto.setMusicianNickname(musicianDto.nickName());
        compositionDto.setMusicianSlug(musicianDto.slug());
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

    @Override
    public Set<Musician> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Musician> musicianBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianBirthList.addAll(musicianRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i), null));
        }
        return musicianBirthList;
    }

    @Override
    public Set<Musician> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Musician> musicianDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianDeathList.addAll(musicianRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i), null));
        }
        return musicianDeathList;
    }

    @Override
    public Set<Musician> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Musician> musicianBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianBirthList.addAll(musicianRepository
                    .findAllWithCurrentBirth(LocalDate.now().plusDays(i), true));
        }
        return musicianBirthList;
    }

    @Override
    public Set<Musician> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Musician> musicianDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            musicianDeathList.addAll(musicianRepository
                    .findAllWithCurrentDeath(LocalDate.now().plusDays(i), true));
        }
        return musicianDeathList;
    }

    @Override
    public String isSlugExists(String musicianSlug) {
        String message = "";
        if (getBySlug(musicianSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return musicianRepository.getSize();
    }
}
