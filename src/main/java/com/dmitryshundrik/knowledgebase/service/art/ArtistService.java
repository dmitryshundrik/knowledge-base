package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.repository.art.ArtistRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.UNKNOWN;

@Service
@Transactional
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final PaintingService paintingService;

    @Transactional(readOnly = true)
    public List<Artist> getAll() {
        return unknownFilter(artistRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<Artist> getAllSortedByBorn() {
        return unknownFilter(artistRepository.getAllByOrderByBorn());
    }

    @Transactional(readOnly = true)
    public List<Artist> getAllSortedByCreatedDesc() {
        return unknownFilter(artistRepository.getAllByOrderByCreatedDesc());
    }

    public List<Artist> unknownFilter(List<Artist> artistList) {
        return artistList.stream()
                .filter(artist -> !Objects.equals(artist.getSlug(), UNKNOWN))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Artist getBySlug(String artistSlug) {
        return artistRepository.getBySlug(artistSlug);
    }

    public Artist createArtist(ArtistCreateEditDto artistDTO) {
        Artist artist = new Artist();
        setFieldsFromDTO(artist, artistDTO);
        artist.setSlug(SlugFormatter.slugFormatter(artist.getSlug()));
        return artistRepository.save(artist);
    }

    public ArtistViewDto updateArtist(String artistSlug, ArtistCreateEditDto artistDTO) {
        Artist bySlug = getBySlug(artistSlug);
        setFieldsFromDTO(bySlug, artistDTO);
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

    public ArtistViewDto getArtistViewDto(Artist artist) {
        return ArtistViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(artist.getCreated()))
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

    public List<ArtistViewDto> getArtistViewDtoList(List<Artist> artistList) {
        return artistList.stream()
                .map(this::getArtistViewDto)
                .collect(Collectors.toList());
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

    public void setFieldsFromDTO(Artist artist, ArtistCreateEditDto artistDto) {
        artist.setSlug(artistDto.getSlug());
        artist.setNickName(artistDto.getNickName().trim());
        artist.setFirstName(artistDto.getFirstName().trim());
        artist.setLastName(artistDto.getLastName().trim());
        artist.setGender(artistDto.getGender());
        artist.setBorn(artistDto.getBorn());
        artist.setDied(artistDto.getDied());
        artist.setBirthDate(artistDto.getBirthDate());
        artist.setDeathDate(artistDto.getDeathDate());
        artist.setApproximateYears(artistDto.getApproximateYears().trim());
        artist.setBirthplace(artistDto.getBirthplace().trim());
        artist.setOccupation(artistDto.getOccupation().trim());
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
