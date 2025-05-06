package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import java.util.List;

public interface MusicGenreService {

    MusicGenre getBySlug(String musicGenreSlug);

    List<MusicGenre> getAllByMusicianOrderByCount(Musician musician);
    List<MusicGenre> getAllClassicalGenres();
    List<MusicGenre> getAllClassicalGenresOrderByTitle();
    List<MusicGenre> getAllContemporaryGenres();
    List<MusicGenre> getAllContemporaryGenresOrderByTitle();
    List<MusicGenre> getFilteredClassicalGenres();
    List<MusicGenre> getFilteredContemporaryGenres();

    MusicGenre createMusicGenre(MusicGenreCreateEditDto genreDto);
    MusicGenre updateMusicGenre(String genreSlug, MusicGenreCreateEditDto genreDto);
    void deleteMusicGenre(MusicGenre genre);

    MusicGenreViewDto getMusicGenreViewDto(MusicGenre genre);
    List<MusicGenreViewDto> getMusicGenreViewDtoList(List<MusicGenre> musicGenreList);
    MusicGenreCreateEditDto getMusicGenreCreateEditDto(MusicGenre genre);

    String isSlugExists(String musicGenreSlug);
}
