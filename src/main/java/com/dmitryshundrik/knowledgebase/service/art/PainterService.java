package com.dmitryshundrik.knowledgebase.service.art;

import com.dmitryshundrik.knowledgebase.model.art.Painter;
import com.dmitryshundrik.knowledgebase.model.art.dto.PainterCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.PainterViewDTO;
import com.dmitryshundrik.knowledgebase.repository.art.PainterRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PainterService {

    @Autowired
    private PainterRepository painterRepository;

    @Autowired
    private PaintingService paintingService;

    public List<Painter> getAll() {
        return painterRepository.findAll();
    }

    public List<Painter> getAllSortedByCreatedDesc() {
        return painterRepository.getAllByOrderByCreatedDesc();
    }

    public Painter getBySlug(String painterSlug) {
        return painterRepository.getBySlug(painterSlug);
    }

    public Painter createPainter(PainterCreateEditDTO painterDTO) {
        Painter painter = new Painter();
        painter.setCreated(Instant.now());
        setFieldsFromDTO(painter, painterDTO);
        painter.setSlug(SlugFormatter.slugFormatter(painter.getSlug()));
        return painterRepository.save(painter);
    }

    public PainterViewDTO updatePainter(String painterSlug, PainterCreateEditDTO painterDTO) {
        Painter bySlug = getBySlug(painterSlug);
        setFieldsFromDTO(bySlug, painterDTO);
        return getPainterViewDTO(bySlug);
    }

    public void updatePainterImageBySlug(String painterSlug, byte[] bytes) {
        if (bytes.length != 0) {
            Painter bySlug = getBySlug(painterSlug);
            bySlug.setImage(new String(bytes));
        }
    }

    public void deletePainterImage(String painterSlug) {
        Painter bySlug = getBySlug(painterSlug);
        bySlug.setImage(null);
    }

    public void deletePainterBySlug(String painterSlug) {
        painterRepository.delete(getBySlug(painterSlug));
    }

    public PainterViewDTO getPainterViewDTO(Painter painter) {
        return PainterViewDTO.builder()
                .created(InstantFormatter.instantFormatterDMY(painter.getCreated()))
                .slug(painter.getSlug())
                .nickName(painter.getNickName())
                .firstName(painter.getFirstName())
                .lastName(painter.getLastName())
                .image(painter.getImage())
                .born(painter.getBorn())
                .died(painter.getDied())
                .birthDate(painter.getBirthDate())
                .deathDate(painter.getDeathDate())
                .approximateYears(painter.getApproximateYears())
                .birthplace(painter.getBirthplace())
                .occupation(painter.getOccupation())
                .paintingList(paintingService
                        .getPaintingViewDTOList(paintingService
                                .getAllByPainterSortedByYear2(painter)))
                .build();
    }

    public List<PainterViewDTO> getPainterViewDTOList(List<Painter> painterList) {
        return painterList.stream()
                .map(this::getPainterViewDTO)
                .collect(Collectors.toList());
    }

    public PainterCreateEditDTO getPainterCreateEditDTO(Painter painter) {
        return PainterCreateEditDTO.builder()
                .slug(painter.getSlug())
                .nickName(painter.getNickName())
                .firstName(painter.getFirstName())
                .lastName(painter.getLastName())
                .image(painter.getImage())
                .born(painter.getBorn())
                .died(painter.getDied())
                .birthDate(painter.getBirthDate())
                .deathDate(painter.getDeathDate())
                .approximateYears(painter.getApproximateYears())
                .birthplace(painter.getBirthplace())
                .occupation(painter.getOccupation())
                .paintingList(paintingService
                        .getPaintingViewDTOList(paintingService
                                .getAllByPainterSortedByYear2(painter)))
                .build();
    }

    public void setFieldsFromDTO(Painter painter, PainterCreateEditDTO painterDTO) {
        painter.setSlug(painterDTO.getSlug());
        painter.setNickName(painterDTO.getNickName().trim());
        painter.setFirstName(painterDTO.getFirstName().trim());
        painter.setLastName(painterDTO.getLastName().trim());
        painter.setBorn(painterDTO.getBorn());
        painter.setDied(painterDTO.getDied());
        painter.setBirthDate(painterDTO.getBirthDate());
        painter.setDeathDate(painterDTO.getDeathDate());
        painter.setApproximateYears(painterDTO.getApproximateYears().trim());
        painter.setBirthplace(painterDTO.getBirthplace().trim());
        painter.setOccupation(painterDTO.getOccupation().trim());

    }

    public String painterSlugIsExist(String painterSlug) {
        String message = "";
        if (getBySlug(painterSlug) != null) {
            message = "slug is already exist";
        }
        return message;
    }

    public List<Painter> getLatestUpdate() {
        return painterRepository.findFirst20ByOrderByCreatedDesc();
    }

}
