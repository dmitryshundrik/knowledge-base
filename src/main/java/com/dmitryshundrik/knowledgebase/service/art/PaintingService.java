package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.dto.art.PaintingCreateEditDto;
import com.dmitryshundrik.knowledgebase.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.repository.art.PaintingRepository;
import com.dmitryshundrik.knowledgebase.service.common.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class PaintingService {

    private final PaintingRepository paintingRepository;

    private final ImageService imageService;

    public List<Painting> getAll() {
        return paintingRepository.findAll();
    }

    public Painting getBySlug(String paintingSlug) {
        return paintingRepository.getBySlug(paintingSlug);
    }

    public List<Painting> getAllSortedByCreatedDesc() {
        return paintingRepository.getAllByOrderByCreatedDesc();
    }

    public List<Painting> getAllByArtistSortedByYear2(Artist artist) {
        return paintingRepository.getAllByArtistOrderByYear2(artist);
    }

    public List<Painting> getAllByArtistSortedByCreatedDesk(Artist artist) {
        return paintingRepository.getAllByArtistOrderByCreatedDesc(artist);
    }

    public List<Painting> getBestPaintingsByArtist(Artist artist) {
        List<Painting> paintingList = paintingRepository.getAllByArtistAndArtistTopRankNotNull(artist);
        return paintingList.stream()
                .sorted(Comparator.comparing(Painting::getArtistTopRank))
                .collect(Collectors.toList());
    }

    public List<Painting> getAllTimeBestPaintings() {
        List<Painting> paintingList = paintingRepository.getAllByAndAllTimeTopRankNotNull();
        return paintingList.stream()
                .sorted(Comparator.comparing(Painting::getAllTimeTopRank))
                .collect(Collectors.toList());
    }

    public PaintingViewDto createPainting(Artist artist, PaintingCreateEditDto paintingDto) {
        Painting painting = new Painting();
        painting.setArtist(artist);
        setFieldsFromDto(painting, paintingDto);
        painting.setSlug(artist.getSlug() + "-" + SlugFormatter.slugFormatter(paintingDto.getSlug()));
        return getPaintingViewDto(paintingRepository.save(painting));
    }

    public Painting updatePainting(String paintingSlug, PaintingCreateEditDto paintingDto) {
        Painting bySlug = getBySlug(paintingSlug);
        setFieldsFromDto(bySlug, paintingDto);
        return bySlug;
    }

    public void deletePaintingBySlug(String paintingSlug) {
        Painting bySlug = getBySlug(paintingSlug);
        paintingRepository.delete(bySlug);
    }

    public PaintingViewDto getPaintingViewDto(Painting painting) {
        return PaintingViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(painting.getCreated()))
                .slug(painting.getSlug())
                .title(painting.getTitle())
                .artistNickname(painting.getArtist().getNickName())
                .artistSlug(painting.getArtist().getSlug())
                .year1(painting.getYear1())
                .year2(painting.getYear2())
                .approximateYears(painting.getApproximateYears())
                .paintingStyles(null)
                .based(painting.getBased())
                .artistTopRank(painting.getArtistTopRank())
                .allTimeTopRank(painting.getAllTimeTopRank())
                .description(painting.getDescription())
                .image(imageService.getImageDTO(painting.getImage()))
                .build();
    }

    public List<PaintingViewDto> getPaintingViewDtoList(List<Painting> paintingList) {
        return paintingList.stream()
                .map(this::getPaintingViewDto)
                .collect(Collectors.toList());
    }

    public PaintingCreateEditDto getArtistCreateEditDto(Painting painting) {
        return PaintingCreateEditDto.builder()
                .slug(painting.getSlug())
                .title(painting.getTitle())
                .artistNickname(painting.getArtist().getNickName())
                .artistSlug(painting.getArtist().getSlug())
                .year1(painting.getYear1())
                .year2(painting.getYear2())
                .approximateYears(painting.getApproximateYears())
                .paintingStyles(null)
                .based(painting.getBased())
                .artistTopRank(painting.getArtistTopRank())
                .allTimeTopRank(painting.getAllTimeTopRank())
                .description(painting.getDescription())
                .image(imageService.getImageDTO(painting.getImage()))
                .build();
    }

    public String paintingSlugIsExist(String paintingSlug) {
        String message = "";
        if (getBySlug(paintingSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    public void setFieldsFromDto(Painting painting, PaintingCreateEditDto paintingDto) {
        painting.setSlug(paintingDto.getSlug().trim());
        painting.setTitle(paintingDto.getTitle().trim());
        painting.setYear1(paintingDto.getYear1());
        painting.setYear2(paintingDto.getYear2());
        painting.setApproximateYears(paintingDto.getApproximateYears().trim());
        painting.setBased(paintingDto.getBased().trim());
        painting.setArtistTopRank(paintingDto.getArtistTopRank());
        painting.setAllTimeTopRank(paintingDto.getAllTimeTopRank());
        painting.setDescription(paintingDto.getDescription());
    }

    public List<Painting> getLatestUpdate() {
        return paintingRepository.findFirst20ByOrderByCreatedDesc();
    }
}
