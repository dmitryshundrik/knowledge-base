package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_ARTWORK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_CATALOG_NUMBER;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_COLLABORATORS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_DESCRIPTION;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_ESSENTIAL_ALBUMS_RANK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_FEATURE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_HIGHLIGHTS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_RATING;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_SLUG;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_TITLE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_YEAR;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.ALBUM_YEAR_END_RANK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_CATALOG_NUMBER;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_DESCRIPTION;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_FEATURE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_HIGHLIGHTS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_LYRICS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_RATING;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_SLUG;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_TITLE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_TRANSLATION;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_YEAR;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_YEAR_END_RANK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_ALBUMS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_ALBUMS_SORT_TYPE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_BASED;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_BIRTH_DATE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_BIRTH_PLACE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_BORN;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_CATALOGUE_TITLE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_COLLABORATIONS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_COMPOSITIONS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_COMPOSITIONS_SORT_TYPE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_DEATH_DATE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_DIED;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_EVENTS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_FIRST_NAME;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_FOUNDED;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_GENDER;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_IMAGE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_LAST_NAME;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_MUSIC_PERIODS;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_NICKNAME;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_NICKNAME_EN;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_OCCUPATION;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_SLUG;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.MUSICIAN_SPOTIFY_LINK;

public class EntityCreator {

    public static Musician getMusician() {
        return Musician.builder()
                .slug(MUSICIAN_SLUG)
                .nickName(MUSICIAN_NICKNAME)
                .nickNameEn(MUSICIAN_NICKNAME_EN)
                .firstName(MUSICIAN_FIRST_NAME)
                .lastName(MUSICIAN_LAST_NAME)
                .gender(MUSICIAN_GENDER)
                .image(MUSICIAN_IMAGE)
                .born(MUSICIAN_BORN)
                .died(MUSICIAN_DIED)
                .founded(MUSICIAN_FOUNDED)
                .birthDate(MUSICIAN_BIRTH_DATE)
                .deathDate(MUSICIAN_DEATH_DATE)
                .birthplace(MUSICIAN_BIRTH_PLACE)
                .based(MUSICIAN_BASED)
                .occupation(MUSICIAN_OCCUPATION)
                .catalogTitle(MUSICIAN_CATALOGUE_TITLE)
                .musicPeriods(MUSICIAN_MUSIC_PERIODS)
                .albumsSortType(MUSICIAN_ALBUMS_SORT_TYPE)
                .compositionsSortType(MUSICIAN_COMPOSITIONS_SORT_TYPE)
                .spotifyLink(MUSICIAN_SPOTIFY_LINK)
                .events(MUSICIAN_EVENTS)
                .albums(MUSICIAN_ALBUMS)
                .collaborations(MUSICIAN_COLLABORATIONS)
                .compositions(MUSICIAN_COMPOSITIONS)
                .build();
    }

    public static Album getAlbum() {
        List<Composition> compositions = new ArrayList<>();
        compositions.add(getComposition());
        return Album.builder()
                .slug(ALBUM_SLUG)
                .title(ALBUM_TITLE)
                .catalogNumber(ALBUM_CATALOG_NUMBER)
                .musician(new Musician())
                .collaborators(ALBUM_COLLABORATORS)
                .feature(ALBUM_FEATURE)
                .artwork(ALBUM_ARTWORK)
                .year(ALBUM_YEAR)
                .musicGenres(ALBUM_MUSIC_GENRES)
                .rating(ALBUM_RATING)
                .yearEndRank(ALBUM_YEAR_END_RANK)
                .essentialAlbumsRank(ALBUM_ESSENTIAL_ALBUMS_RANK)
                .highlights(ALBUM_HIGHLIGHTS)
                .description(ALBUM_DESCRIPTION)
                .compositions(compositions)
                .build();
    }

    public static Composition getComposition() {
        return Composition.builder()
                .slug(COMPOSITION_SLUG)
                .title(COMPOSITION_TITLE)
                .catalogNumber(COMPOSITION_CATALOG_NUMBER)
//                .musician(getMusician())
                .feature(COMPOSITION_FEATURE)
                .year(COMPOSITION_YEAR)
//                .album(new Album())
                .musicGenres(COMPOSITION_MUSIC_GENRES)
                .rating(COMPOSITION_RATING)
                .yearEndRank(COMPOSITION_YEAR_END_RANK)
                .essentialCompositionsRank(COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK)
                .highlights(COMPOSITION_HIGHLIGHTS)
                .description(COMPOSITION_DESCRIPTION)
                .lyrics(COMPOSITION_LYRICS)
                .translation(COMPOSITION_TRANSLATION)
                .build();
    }
}
