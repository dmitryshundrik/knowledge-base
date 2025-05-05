package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianActivityDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDetailedDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface MusicianService extends BaseService {

    Musician getBySlug(String musicianSlug);
    Musician getById(UUID musicianID);
    Musician getByNickname(String nickName);
    MusicianArchiveDetailedDto getMusicianArchiveDetailedDto(Musician musician);

    List<Musician> getAllByUUIDList(List<UUID> uuidList);
    List<Musician> getAllByPeriod(MusicPeriod period);
    List<Musician> getAllBestByPeriod(MusicPeriod period);
    List<MusicianSimpleDto> getAllMusicianSimpleDto();
    List<MusicianSelectDto> getAllMusicianSelectDto();
    List<MusicianSelectDto> getAllActiveMusicianSelectDtoByYear(Integer year);
    List<MusicianArchiveDto> getAllMusicianArchiveDto();
    List<MusicianArchiveDetailedDto> getAllMusicianArchiveDetailedDto();
    List<MusicianActivityDto> getLatestUpdate();

    MusicianViewDto createMusician(MusicianCreateEditDto musicianDto);
    MusicianViewDto updateMusician(String musicianSlug, MusicianCreateEditDto musicianDto);
    void deleteMusician(String musicianSlug);
    void updateMusicianImage(String musicianSlug, byte[] bytes);
    void deleteMusicianImage(String musicianSlug);

    MusicianViewDto getMusicianViewDto(Musician musician);
    MusicianCreateEditDto getMusicianCreateEditDto(Musician musician);

    void setFieldsToAlbumDto(String musicianSlug, AlbumCreateEditDto albumDto);
    void setFieldsToCompositionDto(String musicianSlug, CompositionCreateEditDto compositionDto);

    List<MusicGenre> getSortedMusicGenresByMusician(Musician musician);

    Set<Musician> getAllWithCurrentBirth(Integer dayInterval);
    Set<Musician> getAllWithCurrentDeath(Integer dayInterval);
    Set<Musician> getAllWithCurrentBirthAndNotification(Integer dayInterval);
    Set<Musician> getAllWithCurrentDeathAndNotification(Integer dayInterval);
}
