package com.dmitryshundrik.knowledgebase.service.art.impl;

import com.dmitryshundrik.knowledgebase.mapper.art.ArtistMapper;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.repository.art.ArtistRepository;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    private final ArtistMapper artistMapper;

    public Artist getBySlug(String artistSlug) {
        return artistRepository.findBySlug(artistSlug).orElse(null);
    }

    public List<Artist> getAll() {
        return artistRepository.findAllBy(UNKNOWN);
    }

    public List<Artist> getAllSortedByBorn() {
        return artistRepository.findAllByOrderByBorn(UNKNOWN);
    }

    public List<Artist> getAllSortedByCreatedDesc() {
        return artistRepository.findAllByOrderByCreatedDesc(UNKNOWN);
    }

    public List<Artist> getLatestUpdate() {
        return artistRepository.findFirst20ByOrderByCreatedDesc(UNKNOWN);
    }

    public Artist createArtist(ArtistCreateEditDto artistDTO) {
        Artist artist = artistMapper.toArtist(artistDTO);
        artist.setSlug(SlugFormatter.slugFormatter(artist.getSlug()));
        return artistRepository.save(artist);
    }

    public ArtistViewDto updateArtist(String artistSlug, ArtistCreateEditDto artistDTO) {
        Artist bySlug = getBySlug(artistSlug);
        artistMapper.updateArtist(bySlug, artistDTO);
        return getArtistViewDto(bySlug);
    }

    public void deleteArtistBySlug(String artistSlug) {
        artistRepository.delete(getBySlug(artistSlug));
    }

    public void updateArtistImageBySlug(String artistSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Artist bySlug = getBySlug(artistSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    public void deleteArtistImage(String artistSlug) {
        Artist bySlug = getBySlug(artistSlug);
        bySlug.setImage(null);
    }

    public ArtistViewDto getArtistViewDto(Artist artist) {
        return artistMapper.toArtistViewDto(artist);
    }

    public List<ArtistViewDto> getArtistViewDtoList(List<Artist> artistList) {
        return artistList.stream()
                .map(this::getArtistViewDto)
                .toList();
    }

    public ArtistCreateEditDto getArtistCreateEditDto(Artist artist) {
        return artistMapper.toArtistCreateEditDto(artist);
    }

    public Set<Artist> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i)));
        }
        return artistBirthList;
    }

    public Set<Artist> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i)));
        }
        return artistDeathList;
    }

    public Set<Artist> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository
                    .findAllWithCurrentBirthAndNotification(LocalDate.now().plusDays(i), true));
        }
        return artistBirthList;
    }

    public Set<Artist> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository
                    .findAllWithCurrentDeathAndNotification(LocalDate.now().plusDays(i), true));
        }
        return artistDeathList;
    }

    public String isSlugExist(String artistSlug) {
        String message = "";
        if (getBySlug(artistSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public Long getRepositorySize() {
        return artistRepository.getSize();
    }
}
