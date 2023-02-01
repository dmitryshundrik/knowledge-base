package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianViewDTO;
import com.dmitryshundrik.knowledgebase.repository.music.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MusicianService {

    @Autowired
    private MusicianRepository musicianRepository;

    public List<Musician> getAllMusicians() {
        return musicianRepository.getAllByOrderById();
    }

    public Musician getMusicianBySlug(String slug) {
        return musicianRepository.getMusicianBySlug(slug);
    }

    public void deleteMusicianBySlug(String slug) {
        musicianRepository.deleteBySlug(slug);
    }

    public void updateMusician(MusicianCreateEditDTO musicianCreateEditDTO, String slug) {
        Musician musicianBySlug = getMusicianBySlug(slug);
        musicianBySlug.setFirstName(musicianCreateEditDTO.getFirstName());
        musicianBySlug.setLastName(musicianCreateEditDTO.getLastName());
        musicianBySlug.setNickName(musicianCreateEditDTO.getNickName());
        musicianBySlug.setBorn(musicianCreateEditDTO.getBorn());
        musicianBySlug.setDied(musicianCreateEditDTO.getDied());
        musicianBySlug.setBirthDate(musicianCreateEditDTO.getBirthDate());
        musicianBySlug.setDeathDate(musicianCreateEditDTO.getDeathDate());
        musicianBySlug.setPeriod(musicianCreateEditDTO.getPeriod());
        musicianBySlug.setStyles(musicianCreateEditDTO.getStyles());

    }

    public List<MusicianViewDTO> musiciansListToMusiciansViewDTOList(List<Musician> musicianList) {
        return musicianList.stream().map(musician -> MusicianViewDTO.builder()
                .id(musician.getId())
                .slug(musician.getSlug())
                .firstName(musician.getFirstName())
                .lastName(musician.getLastName())
                .nickName(musician.getNickName())
                .born(musician.getBorn())
                .died(musician.getDied())
                .birthDate(musician.getBirthDate())
                .deathDate(musician.getDeathDate())
                .period(musician.getPeriod())
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
                .events(musician.getEvents())
                .compositions(musician.getCompositions())
                .build();
    }

}
