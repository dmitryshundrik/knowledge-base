package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import com.dmitryshundrik.knowledgebase.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicianService {

    @Autowired
    private MusicianRepository musicianRepository;

    @Autowired
    private EventService eventService;

    public List<Musician> getAllMusicians() {
        return musicianRepository.getAllByOrderByCreated();
    }

    public Musician getMusicianBySlug(String slug) {
        return musicianRepository.getMusicianBySlug(slug);
    }

    public void deleteMusicianBySlug(String slug) {
        musicianRepository.deleteBySlug(slug);
    }

    public void updateExistingMusician(MusicianCreateEditDTO musicianCreateEditDTO, String slug) {
        Musician musicianBySlug = getMusicianBySlug(slug);
        setUpMusicianFieldsFromDTO(musicianBySlug, musicianCreateEditDTO);
    }

    public void createMusicianByMusicianDTO(MusicianCreateEditDTO musicianCreateEditDTO) {
        Musician musician = new Musician();
        musician.setCreated(Instant.now());
        setUpMusicianFieldsFromDTO(musician, musicianCreateEditDTO);
        musicianRepository.save(musician);
    }

    public List<MusicianViewDTO> musiciansListToMusiciansViewDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(musician -> MusicianViewDTO.builder()
                .created(musician.getCreated())
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .born(musician.getBorn())
                .died(musician.getDied())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .period(musician.getPeriod())
                .styles(musician.getStyles())
                .genres(musician.getGenres())
                .build()).collect(Collectors.toList());
    }

    public MusicianCreateEditDTO musicianToMusicianCreateEditDTO(Musician musician) {
        return MusicianCreateEditDTO.builder()
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .image(musician.getImage())
                .born(musician.getBorn())
                .died(musician.getDied())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .period(musician.getPeriod())
                .styles(musician.getStyles())
                .genres(musician.getGenres())
                .events(eventService.eventListToEventDTOList(musician.getEvents()))
                .build();
    }

    private void setUpMusicianFieldsFromDTO(Musician musician, MusicianCreateEditDTO musicianCreateEditDTO) {
        musician.setSlug(musicianCreateEditDTO.getSlug());
        musician.setNickName(musicianCreateEditDTO.getNickName());
        musician.setFirstName(musicianCreateEditDTO.getFirstName());
        musician.setLastName(musicianCreateEditDTO.getLastName());
        musician.setBorn(musicianCreateEditDTO.getBorn());
        musician.setDied(musicianCreateEditDTO.getDied());
        musician.setBirthDate(musicianCreateEditDTO.getBirthDate());
        musician.setDeathDate(musicianCreateEditDTO.getDeathDate());
        musician.setPeriod(musicianCreateEditDTO.getPeriod());
        musician.setStyles(musicianCreateEditDTO.getStyles());
        musician.setGenres(musicianCreateEditDTO.getGenres());
    }
}
