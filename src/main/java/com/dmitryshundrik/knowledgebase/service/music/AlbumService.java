package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.AlbumRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CompositionService compositionService;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();

    }

    public void createAlbumByAlbumDTO(AlbumCreateEditDTO albumCreateEditDTO, Musician musician) {
        Album album = new Album();
        album.setCreated(Instant.now());
        album.setMusician(musician);
        setUpALbumFieldsFromDTO(album, albumCreateEditDTO);
        albumRepository.save(album);
    }

    public List<AlbumViewDTO> getAlbumViewDTOList(List<Album> albumList) {
        return albumList.stream().map(album -> AlbumViewDTO.builder()
                .created(Formatter.instantFormatter(album.getCreated()))
                .slug(album.getSlug())
                .title(album.getTitle())
                .catalogNumber(album.getCatalogNumber())
                .musicianNickname(album.getMusician().getNickName())
                .musicianSlug(album.getMusician().getSlug())
                .artwork(album.getArtwork())
                .year(album.getYear())
                .period(album.getPeriod())
                .academicGenres(album.getAcademicGenres())
                .contemporaryGenres(album.getContemporaryGenres())
                .rating(album.getRating())
                .yearEndRank(album.getYearEndRank())
                .highlights(album.getHighlights())
                .description(album.getDescription())
                .compositions(compositionService.getCompositionViewDTOList(album.getCompositions()))
                .build()).collect(Collectors.toList());

    }

    private void setUpALbumFieldsFromDTO(Album album, AlbumCreateEditDTO albumCreateEditDTO) {
        album.setSlug(albumCreateEditDTO.getSlug());
        album.setTitle(albumCreateEditDTO.getTitle());
        album.setCatalogNumber(albumCreateEditDTO.getCatalogNumber());
        album.setArtwork(albumCreateEditDTO.getArtwork());
        album.setYear(albumCreateEditDTO.getYear());
        album.setPeriod(albumCreateEditDTO.getPeriod());
        album.setAcademicGenres(albumCreateEditDTO.getAcademicGenres());
        album.setContemporaryGenres(albumCreateEditDTO.getContemporaryGenres());
        album.setRating(albumCreateEditDTO.getRating());
        album.setYearEndRank(albumCreateEditDTO.getYearEndRank());
        album.setHighlights(albumCreateEditDTO.getHighlights());
        album.setDescription(albumCreateEditDTO.getDescription());
    }
}
