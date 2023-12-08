package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import com.dmitryshundrik.knowledgebase.model.art.Painting;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PaintingViewDTO;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.repository.art.PaintingRepository;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.*;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaintingService {

    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private ImageService imageService;

    public List<Painting> getAll() {
        return paintingRepository.findAll();
    }

    public Painting getBySlug(String paintingSlug) {
        return paintingRepository.getBySlug(paintingSlug);
    }

    public List<Painting> getAllSortedByCreatedDesc() {
        return paintingRepository.getAllByOrderByCreatedDesc();
    }

    public List<Painting> getAllByPainterSortedByYear2(Painter painter) {
        return paintingRepository.getAllByPainterOrderByYear2(painter);
    }

    public List<Painting> getAllByPainterSortedByCreatedDesk(Painter painter) {
        return paintingRepository.getAllByPainterOrderByCreatedDesc(painter);
    }

    public List<Painting> getBestPaintingsByPainter(Painter painter) {
        List<Painting> paintingList = paintingRepository.getAllByPainterAndPainterTopRankNotNull(painter);
        return paintingList.stream()
                .sorted(Comparator.comparing(Painting::getPainterTopRank))
                .collect(Collectors.toList());
    }

    public List<Painting> getAllTimeBestPaintings() {
        List<Painting> paintingList = paintingRepository.getAllByAndAllTimeTopRankNotNull();
        return paintingList.stream()
                .sorted(Comparator.comparing(Painting::getAllTimeTopRank))
                .collect(Collectors.toList());
    }

    public PaintingViewDTO createPainting(Painter painter, PaintingCreateEditDTO paintingDTO) {
        Painting painting = new Painting();
        painting.setCreated(Instant.now());
        painting.setPainter(painter);
        setFieldsFromDTO(painting, paintingDTO);
        painting.setSlug(painter.getSlug() + "-" + SlugFormatter.slugFormatter(paintingDTO.getSlug()));
        return getPaintingViewDTO(paintingRepository.save(painting));
    }

    public Painting updatePainting(String paintingSlug, PaintingCreateEditDTO paintingDTO) {
        Painting bySlug = getBySlug(paintingSlug);
        setFieldsFromDTO(bySlug, paintingDTO);
        return bySlug;
    }

    public void deletePaintingBySlug(String paintingSlug) {
        Painting bySlug = getBySlug(paintingSlug);
        paintingRepository.delete(bySlug);
    }

    public PaintingViewDTO getPaintingViewDTO(Painting painting) {
        return PaintingViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(painting.getCreated()))
                .slug(painting.getSlug())
                .title(painting.getTitle())
                .painterNickname(painting.getPainter().getNickName())
                .painterSlug(painting.getPainter().getSlug())
                .year1(painting.getYear1())
                .year2(painting.getYear2())
                .approximateYears(painting.getApproximateYears())
                .paintingStyles(null)
                .based(painting.getBased())
                .painterTopRank(painting.getPainterTopRank())
                .allTimeTopRank(painting.getAllTimeTopRank())
                .description(painting.getDescription())
                .image(imageService.getImageDTO(painting.getImage()))
                .build();
    }

    public List<PaintingViewDTO> getPaintingViewDTOList(List<Painting> paintingList) {
        return paintingList.stream()
                .map(this::getPaintingViewDTO)
                .collect(Collectors.toList());
    }

    public PaintingCreateEditDTO getPainterCreateEditDTO(Painting painting) {
        return PaintingCreateEditDTO.builder()
                .slug(painting.getSlug())
                .title(painting.getTitle())
                .painterNickname(painting.getPainter().getNickName())
                .painterSlug(painting.getPainter().getSlug())
                .year1(painting.getYear1())
                .year2(painting.getYear2())
                .approximateYears(painting.getApproximateYears())
                .paintingStyles(null)
                .based(painting.getBased())
                .painterTopRank(painting.getPainterTopRank())
                .allTimeTopRank(painting.getAllTimeTopRank())
                .description(painting.getDescription())
                .image(imageService.getImageDTO(painting.getImage()))
                .build();
    }

    public String paintingSlugIsExist(String paintingSlug) {
        String message = "";
        if (getBySlug(paintingSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

    public void setFieldsFromDTO(Painting painting, PaintingCreateEditDTO paintingDTO) {
        painting.setSlug(paintingDTO.getSlug().trim());
        painting.setTitle(paintingDTO.getTitle().trim());
        painting.setYear1(paintingDTO.getYear1());
        painting.setYear2(paintingDTO.getYear2());
        painting.setApproximateYears(paintingDTO.getApproximateYears().trim());
        painting.setBased(paintingDTO.getBased().trim());
        painting.setPainterTopRank(paintingDTO.getPainterTopRank());
        painting.setAllTimeTopRank(paintingDTO.getAllTimeTopRank());
        painting.setDescription(paintingDTO.getDescription());
    }

    public List<Painting> getLatestUpdate() {
        return paintingRepository.findFirst20ByOrderByCreatedDesc();
    }

}
