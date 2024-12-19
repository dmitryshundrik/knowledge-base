package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.exception.NotFoundException;
import com.dmitryshundrik.knowledgebase.mapper.art.ArtistMapper;
import com.dmitryshundrik.knowledgebase.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.repository.art.ArtistRepository;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST_NOT_FOUND_BY_SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final PaintingService paintingService;

    private final ArtistMapper artistMapper;

    public List<Artist> getAll() {
        return unknownFilter(artistRepository.findAll());
    }

    public List<Artist> getAllSortedByBorn() {
        return unknownFilter(artistRepository.getAllByOrderByBorn());
    }

    public List<Artist> getAllSortedByCreatedDesc() {
        return unknownFilter(artistRepository.getAllByOrderByCreatedDesc());
    }

    public Artist getBySlug(String artistSlug) {
        return artistRepository.getBySlug(artistSlug)
                .orElseThrow(() -> new NotFoundException(ARTIST_NOT_FOUND_BY_SLUG.formatted(artistSlug)));
    }

    public Artist createArtist(ArtistCreateEditDto artistDTO) {
        Artist artist = artistMapper.toArtist(artistDTO);
        artist.setSlug(SlugFormatter.slugFormatter(artist.getSlug()));
        return artistRepository.save(artist);
    }

    public ArtistViewDto updateArtist(String artistSlug, ArtistCreateEditDto artistDTO) {
        Artist bySlug = getBySlug(artistSlug);
        artistMapper.updateArtist(artistDTO, bySlug);
        return getArtistViewDto(bySlug);
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

    public void deleteArtistBySlug(String artistSlug) {
        artistRepository.delete(getBySlug(artistSlug));
    }

    public List<Artist> unknownFilter(List<Artist> artistList) {
        return artistList.stream()
                .filter(artist -> !Objects.equals(artist.getSlug(), UNKNOWN))
                .toList();
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
        return ArtistCreateEditDto.builder()
                .slug(artist.getSlug())
                .nickName(artist.getNickName())
                .firstName(artist.getFirstName())
                .lastName(artist.getLastName())
                .gender(artist.getGender())
                .image(artist.getImage())
                .born(artist.getBorn())
                .died(artist.getDied())
                .birthDate(artist.getBirthDate())
                .deathDate(artist.getDeathDate())
                .approximateYears(artist.getApproximateYears())
                .birthplace(artist.getBirthplace())
                .occupation(artist.getOccupation())
                .paintingList(paintingService
                        .getPaintingViewDtoList(paintingService
                                .getAllByArtistSortedByYear2(artist)))
                .build();
    }

    public String artistSlugIsExist(String artistSlug) {
        String message = "";
        if (getBySlug(artistSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public List<Artist> getLatestUpdate() {
        return unknownFilter(artistRepository.findFirst20ByOrderByCreatedDesc());
    }

    public Set<Artist> getAllWithCurrentBirth() {
        Set<Artist> artistBirthList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            artistBirthList.addAll(artistRepository.findAllWithCurrentBirth(LocalDate.now().plusDays(i)));
        }
        return artistBirthList;
    }

    public Set<Artist> getAllWithCurrentDeath() {
        Set<Artist> artistDeathList = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            artistDeathList.addAll(artistRepository.findAllWithCurrentDeath(LocalDate.now().plusDays(i)));
        }
        return artistDeathList;
    }
}
