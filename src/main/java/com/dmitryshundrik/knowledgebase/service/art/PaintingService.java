package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingArtistProfileDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

/**
 * Service interface for managing {@link Painting} entities.
 * Provides methods for retrieving, creating, and updating paintings,
 * as well as accessing painting-related data such as rankings and artist associations.
 */
public interface PaintingService extends BaseService {

    Painting getBySlug(String paintingSlug);

    List<Painting> getAll();
    List<Painting> getAllSortedByCreatedDesc();
    List<Painting> getAllByArtistSortedByYear2(Artist artist, String direction);
    List<PaintingArtistProfileDto> getAllProfileDtoByArtistSortedByYear2Asc(Artist artist);
    List<PaintingSimpleDto> getBestPaintingsByArtist(Artist artist);
    List<Painting> getAllTimeBestPaintings();
    List<Painting> getLatestUpdate();

    PaintingViewDto createPainting(Artist artist, PaintingCreateEditDto paintingDto);
    Painting updatePainting(String paintingSlug, PaintingCreateEditDto paintingDto);
    void deletePaintingBySlug(String paintingSlug);

    PaintingViewDto getPaintingViewDto(Painting painting);
    List<PaintingViewDto> getPaintingViewDtoList(List<Painting> paintingList);
    PaintingCreateEditDto getArtistCreateEditDto(Painting painting);
}