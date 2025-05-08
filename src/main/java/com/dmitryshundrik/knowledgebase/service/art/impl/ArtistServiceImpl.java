package com.dmitryshundrik.knowledgebase.service.art.impl;

import com.dmitryshundrik.knowledgebase.mapper.art.ArtistMapper;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.repository.art.ArtistRepository;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.baseFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    @Override
    public Artist getBySlug(String artistSlug) {
        return artistRepository.findBySlug(artistSlug).orElse(null);
    }

    @Override
    public List<Artist> getAllOrderByBorn() {
        return artistRepository.findAllByOrderByBorn(UNKNOWN);
    }

    @Override
    public List<Artist> getAllOrderByCreatedDesc() {
        return artistRepository.findAllByOrderByCreatedDesc(UNKNOWN);
    }

    @Override
    public List<Artist> getLatestUpdate() {
        return artistRepository.findFirst20ByOrderByCreatedDesc(UNKNOWN);
    }

    @Override
    public Artist createArtist(ArtistCreateEditDto artistDto) {
        Artist artist = artistMapper.toArtist(artistDto);
        artistRepository.save(artist);
        artist.setSlug(baseFormatter(artist.getSlug()));
        return artist;
    }

    @Override
    public Artist updateArtist(String artistSlug, ArtistCreateEditDto artistDto) {
        Artist artist = getBySlug(artistSlug);
        artistMapper.updateArtist(artist, artistDto);
        artist.setSlug(baseFormatter(artist.getSlug()));
        return artist;
    }

    @Override
    public void deleteArtist(String artistSlug) {
        artistRepository.delete(getBySlug(artistSlug));
    }

    @Override
    public void updateArtistImageBySlug(String artistSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Artist bySlug = getBySlug(artistSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    @Override
    public void deleteArtistImage(String artistSlug) {
        Artist bySlug = getBySlug(artistSlug);
        bySlug.setImage(null);
    }

    @Override
    public ArtistViewDto getArtistViewDto(Artist artist) {
        return artistMapper.toArtistViewDto(new ArtistViewDto(), artist);
    }

    public List<ArtistViewDto> getArtistViewDtoList(List<Artist> artistList) {
        return artistList.stream()
                .map(this::getArtistViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArtistCreateEditDto getArtistCreateEditDto(Artist artist) {
        return artistMapper.toArtistCreateEditDto(new ArtistCreateEditDto(), artist);
    }

    @Override
    public Set<Artist> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i), null));
        }
        return artistBirthList;
    }

    @Override
    public Set<Artist> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i), null));
        }
        return artistDeathList;
    }

    @Override
    public Set<Artist> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository
                    .findAllWithCurrentBirth(LocalDate.now().plusDays(i), true));
        }
        return artistBirthList;
    }

    @Override
    public Set<Artist> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository
                    .findAllWithCurrentDeath(LocalDate.now().plusDays(i), true));
        }
        return artistDeathList;
    }

    @Override
    public String isSlugExists(String artistSlug) {
        String message = "";
        if (getBySlug(artistSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return artistRepository.getSize();
    }
}
