package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;
import java.util.Set;

/**
 * Service interface for managing {@link Artist} entities.
 * Provides methods for retrieving, creating, updating, and deleting artists, as well as handling
 * artist-related data such as images, birth/death notifications, and DTO conversions.
 */
public interface ArtistService extends BaseService {

    Artist getBySlug(String artistSlug);

    List<Artist> getAll();
    List<Artist> getAllOrderByBorn();
    List<Artist> getAllOrderByCreatedDesc();
    List<Artist> getLatestUpdate();

    Artist createArtist(ArtistCreateEditDto artistDto);
    ArtistViewDto updateArtist(String artistSlug, ArtistCreateEditDto artistDto);
    void deleteArtistBySlug(String artistSlug);

    void updateArtistImageBySlug(String artistSlug, byte[] imageBytes);
    void deleteArtistImage(String artistSlug);

    ArtistViewDto getArtistViewDto(Artist artist);
    List<ArtistViewDto> getArtistViewDtoList(List<Artist> artistList);
    ArtistCreateEditDto getArtistCreateEditDto(Artist artist);

    Set<Artist> getAllWithCurrentBirth(Integer dayInterval);
    Set<Artist> getAllWithCurrentDeath(Integer dayInterval);
    Set<Artist> getAllWithCurrentBirthAndNotification(Integer dayInterval);
    Set<Artist> getAllWithCurrentDeathAndNotification(Integer dayInterval);
}