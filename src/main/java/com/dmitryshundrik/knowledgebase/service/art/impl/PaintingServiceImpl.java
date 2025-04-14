package com.dmitryshundrik.knowledgebase.service.art.impl;

import com.dmitryshundrik.knowledgebase.mapper.art.PaintingMapper;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.repository.art.PaintingRepository;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SORT_DIRECTION_ASC;

@Service
@Transactional
@RequiredArgsConstructor
public class PaintingServiceImpl implements PaintingService {

    private final PaintingRepository paintingRepository;

    private final PaintingMapper paintingMapper;

    public Painting getBySlug(String paintingSlug) {
        return paintingRepository.findBySlug(paintingSlug);
    }

    public List<Painting> getAll() {
        return paintingRepository.findAll();
    }

    public List<Painting> getAllSortedByCreatedDesc() {
        return paintingRepository.findAllByOrderByCreatedDesc();
    }

    public List<Painting> getAllByArtistSortedByYear2(Artist artist, String sortDirection) {
        if (SORT_DIRECTION_ASC.equalsIgnoreCase(sortDirection)) {
            return paintingRepository.findAllByArtistOrderByYear2Asc(artist);
        }
        return paintingRepository.findAllByArtistOrderByYear2Desc(artist);
    }

    public List<PaintingSimpleDto> getBestPaintingsByArtist(Artist artist) {
        return paintingRepository.findAllByArtistAndArtistTopRankNotNull(artist);
    }

    public List<Painting> getAllTimeBestPaintings() {
        List<Painting> paintingList = paintingRepository.findAllByAllTimeTopRankNotNull();
        return paintingList.stream().sorted(Comparator.comparing(Painting::getAllTimeTopRank)).collect(Collectors.toList());
    }

    public List<Painting> getLatestUpdate() {
        return paintingRepository.findFirst20ByOrderByCreatedDesc();
    }

    public PaintingViewDto createPainting(Artist artist, PaintingCreateEditDto paintingDto) {
        Painting painting = new Painting();
        painting = paintingMapper.toPainting(painting, paintingDto);
        painting.setArtist(artist);
        painting.setSlug(artist.getSlug() + "-" + SlugFormatter.slugFormatter(paintingDto.getSlug()));
        return getPaintingViewDto(paintingRepository.save(painting));
    }

    public Painting updatePainting(String paintingSlug, PaintingCreateEditDto paintingDto) {
        Painting bySlug = getBySlug(paintingSlug);
        return paintingMapper.toPainting(bySlug, paintingDto);
    }

    public void deletePaintingBySlug(String paintingSlug) {
        Painting bySlug = getBySlug(paintingSlug);
        paintingRepository.delete(bySlug);
    }

    public PaintingViewDto getPaintingViewDto(Painting painting) {
        return paintingMapper.toPaintingViewDto(new PaintingViewDto(), painting);
    }

    public List<PaintingViewDto> getPaintingViewDtoList(List<Painting> paintingList) {
        return paintingList.stream().map(this::getPaintingViewDto).collect(Collectors.toList());
    }

    public PaintingCreateEditDto getArtistCreateEditDto(Painting painting) {
        return paintingMapper.toPaintingCreateEditDto(painting);
    }

    public String isSlugExist(String paintingSlug) {
        String message = "";
        if (getBySlug(paintingSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public Long getRepositorySize() {
        return paintingRepository.getSize();
    }
}
