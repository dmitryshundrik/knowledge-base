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

    @Override
    public Artist getBySlug(String artistSlug) {
        return artistRepository.findBySlug(artistSlug).orElse(null);
    }

    @Override
    public List<Artist> getAll() {
        return artistRepository.findAllBy(UNKNOWN);
    }

    @Override
    public List<Artist> getAllSortedByBorn() {
        return artistRepository.findAllByOrderByBorn(UNKNOWN);
    }

    @Override
    public List<Artist> getAllSortedByCreatedDesc() {
        return artistRepository.findAllByOrderByCreatedDesc(UNKNOWN);
    }

    @Override
    public List<Artist> getLatestUpdate() {
        return artistRepository.findFirst20ByOrderByCreatedDesc(UNKNOWN);
    }

    @Override
    public Artist createArtist(ArtistCreateEditDto artistDTO) {
        Artist artist = artistMapper.toArtist(artistDTO);
        artist.setSlug(SlugFormatter.slugFormatter(artist.getSlug()));
        return artistRepository.save(artist);
    }

    @Override
    public ArtistViewDto updateArtist(String artistSlug, ArtistCreateEditDto artistDTO) {
        Artist bySlug = getBySlug(artistSlug);
        artistMapper.updateArtist(bySlug, artistDTO);
        return getArtistViewDto(bySlug);
    }

    @Override
    public void deleteArtistBySlug(String artistSlug) {
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
        return artistMapper.toArtistViewDto(artist);
    }

    public List<ArtistViewDto> getArtistViewDtoList(List<Artist> artistList) {
        return artistList.stream()
                .map(this::getArtistViewDto)
                .toList();
    }

    @Override
    public ArtistCreateEditDto getArtistCreateEditDto(Artist artist) {
        return artistMapper.toArtistCreateEditDto(artist);
    }

    @Override
    public Set<Artist> getAllWithCurrentBirth(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i)));
        }
        return artistBirthList;
    }

    @Override
    public Set<Artist> getAllWithCurrentDeath(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i)));
        }
        return artistDeathList;
    }

    @Override
    public Set<Artist> getAllWithCurrentBirthAndNotification(Integer dayInterval) {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistBirthList.addAll(artistRepository
                    .findAllWithCurrentBirthAndNotification(LocalDate.now().plusDays(i), true));
        }
        return artistBirthList;
    }

    @Override
    public Set<Artist> getAllWithCurrentDeathAndNotification(Integer dayInterval) {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < dayInterval; i++) {
            artistDeathList.addAll(artistRepository
                    .findAllWithCurrentDeathAndNotification(LocalDate.now().plusDays(i), true));
        }
        return artistDeathList;
    }

    @Override
    public String isSlugExist(String artistSlug) {
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
