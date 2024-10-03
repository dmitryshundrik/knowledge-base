package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistViewDTO;
import com.dmitryshundrik.knowledgebase.repository.art.ArtistRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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
public class ArtistService {

    private final ArtistRepository artistRepository;

    private final PaintingService paintingService;

    public ArtistService(ArtistRepository artistRepository, PaintingService paintingService) {
        this.artistRepository = artistRepository;
        this.paintingService = paintingService;
    }

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

    public Artist createArtist(ArtistCreateEditDTO artistDTO) {
        Artist artist = new Artist();
        artist.setCreated(Instant.now());
        setFieldsFromDTO(artist, artistDTO);
        artist.setSlug(SlugFormatter.slugFormatter(artist.getSlug()));
        return artistRepository.save(artist);
    }

    public ArtistViewDTO updateArtist(String artistSlug, ArtistCreateEditDTO artistDTO) {
        Artist bySlug = getBySlug(artistSlug);
        setFieldsFromDTO(bySlug, artistDTO);
        return getArtistViewDTO(bySlug);
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

    public ArtistViewDTO getArtistViewDTO(Artist artist) {
        return ArtistViewDTO.builder()
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
                        .getPaintingViewDTOList(paintingService
                                .getAllByArtistSortedByYear2(artist)))
                .build();
    }

    public List<ArtistViewDTO> getArtistViewDTOList(List<Artist> artistList) {
        return artistList.stream()
                .map(this::getArtistViewDTO)
                .collect(Collectors.toList());
    }

    public ArtistCreateEditDTO getArtistCreateEditDTO(Artist artist) {
        return ArtistCreateEditDTO.builder()
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
                        .getPaintingViewDTOList(paintingService
                                .getAllByArtistSortedByYear2(artist)))
                .build();
    }

    public void setFieldsFromDTO(Artist artist, ArtistCreateEditDTO artistDTO) {
        artist.setSlug(artistDTO.getSlug());
        artist.setNickName(artistDTO.getNickName().trim());
        artist.setFirstName(artistDTO.getFirstName().trim());
        artist.setLastName(artistDTO.getLastName().trim());
        artist.setGender(artistDTO.getGender());
        artist.setBorn(artistDTO.getBorn());
        artist.setDied(artistDTO.getDied());
        artist.setBirthDate(artistDTO.getBirthDate());
        artist.setDeathDate(artistDTO.getDeathDate());
        artist.setApproximateYears(artistDTO.getApproximateYears().trim());
        artist.setBirthplace(artistDTO.getBirthplace().trim());
        artist.setOccupation(artistDTO.getOccupation().trim());

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
