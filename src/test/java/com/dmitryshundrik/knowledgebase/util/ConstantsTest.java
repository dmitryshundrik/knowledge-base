package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConstantsTest {

    public static final String MUSICIAN_SLUG = "musician_slug";
    public static final String MUSICIAN_NICKNAME = "musician_nickname";
    public static final String MUSICIAN_NICKNAME_EN = "musician_nickname_en";
    public static final String MUSICIAN_FIRST_NAME = "musician_first_name";
    public static final String MUSICIAN_LAST_NAME = "musician_last_name";
    public static final Gender MUSICIAN_GENDER = Gender.MALE;
    public static final String MUSICIAN_IMAGE = "musician_image";
    public static final Integer MUSICIAN_BORN = 1821;
    public static final Integer MUSICIAN_DIED = 1881;
    public static final Integer MUSICIAN_FOUNDED = 2000;
    public static final LocalDate MUSICIAN_BIRTH_DATE = LocalDate.of(2000, 1, 1);
    public static final LocalDate MUSICIAN_DEATH_DATE = LocalDate.of(2020, 1, 1);
    public static final String MUSICIAN_BIRTH_PLACE = "musician_birth_place";
    public static final String MUSICIAN_BASED = "musician_based";
    public static final String MUSICIAN_OCCUPATION = "musician_occupation";
    public static final String MUSICIAN_CATALOGUE_TITLE = "musician_catalogue_title";
    public static final List<MusicPeriod> MUSICIAN_MUSIC_PERIODS = new ArrayList<>();
    public static final SortType MUSICIAN_ALBUMS_SORT_TYPE = SortType.YEAR;
    public static final SortType MUSICIAN_COMPOSITIONS_SORT_TYPE = SortType.YEAR;
    public static final String MUSICIAN_SPOTIFY_LINK = "spotify_link";
    public static final List<PersonEvent> MUSICIAN_EVENTS = new ArrayList<>();
    public static final List<Album> MUSICIAN_ALBUMS = new ArrayList<>();
    public static final List<Album> MUSICIAN_COLLABORATIONS = new ArrayList<>();
    public static final List<Composition> MUSICIAN_COMPOSITIONS = new ArrayList<>();

    public static final String ALBUM_SLUG = "album_slug";
    public static final String ALBUM_TITLE = "album_title";
    public static final String ALBUM_CATALOG_NUMBER = "album_catalog_number";
    public static final List<Musician> ALBUM_COLLABORATORS = new ArrayList<>();
    public static final String ALBUM_FEATURE = "album_feature";
    public static final String ALBUM_ARTWORK = "album_artwork";
    public static final Integer ALBUM_YEAR = 2020;
    public static final List<MusicGenre> ALBUM_MUSIC_GENRES = new ArrayList<>();
    public static final Double ALBUM_RATING = 9.5;
    public static final Integer ALBUM_YEAR_END_RANK = 1;
    public static final Integer ALBUM_ESSENTIAL_ALBUMS_RANK = 1;
    public static final String ALBUM_HIGHLIGHTS = "album_highlights";
    public static final String ALBUM_DESCRIPTION = "album_description";

    public static final String COMPOSITION_SLUG = "composition_slug";
    public static final String COMPOSITION_TITLE = "composition_title";
    public static final Integer COMPOSITION_CATALOG_NUMBER = 1;
    public static final String COMPOSITION_MUSICIAN_NICKNAME = "musician_nickname";
    public static final String COMPOSITION_MUSICIAN_SLUG = "musician_slug";
    public static final String COMPOSITION_ALBUM_ID = "album_id";
    public static final String COMPOSITION_FEATURE = "composition_feature";
    public static final Integer COMPOSITION_YEAR = 1990;
    public static final List<MusicGenre> COMPOSITION_CLASSICAL_GENRES = new ArrayList<>();
    public static final List<MusicGenre> COMPOSITION_CONTEMPORARY_GENRES = new ArrayList<>();
    public static final List<MusicGenre> COMPOSITION_MUSIC_GENRES = new ArrayList<>();
    public static final Double COMPOSITION_RATING = 9.5;
    public static final Integer COMPOSITION_YEAR_END_RANK = 1;
    public static final Integer COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK = 1;
    public static final String COMPOSITION_HIGHLIGHTS = "composition_highlights";
    public static final String COMPOSITION_DESCRIPTION = "composition_description";
    public static final String COMPOSITION_LYRICS = "composition_lyrics";
    public static final String COMPOSITION_TRANSLATION = "composition_translation";
}
