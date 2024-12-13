package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.dto.music.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.music.CompositionViewDTO;

import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_ALBUM_ID;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_CATALOG_NUMBER_DOUBLE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_CATALOG_NUMBER_STRING;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_CLASSICAL_GENRES;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_CONTEMPORARY_GENRES;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_FEATURE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_MUSICIAN_NICKNAME;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_MUSICIAN_SLUG;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_RATING;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_SLUG;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_TITLE;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_YEAR;
import static com.dmitryshundrik.knowledgebase.util.ConstantsTest.COMPOSITION_YEAR_END_RANK;

public class DtoCreator {

    public static CompositionCreateEditDTO getCompositionCreateEditDTO() {
        return CompositionCreateEditDTO.builder()
                .slug(COMPOSITION_SLUG)
                .title(COMPOSITION_TITLE)
                .catalogNumber(COMPOSITION_CATALOG_NUMBER_DOUBLE)
                .musicianNickname(COMPOSITION_MUSICIAN_NICKNAME)
                .musicianSlug(COMPOSITION_MUSICIAN_SLUG)
                .albumId(COMPOSITION_ALBUM_ID)
                .feature(COMPOSITION_FEATURE)
                .year(COMPOSITION_YEAR)
                .classicalGenres(COMPOSITION_CLASSICAL_GENRES)
                .contemporaryGenres(COMPOSITION_CONTEMPORARY_GENRES)
                .rating(COMPOSITION_RATING)
                .yearEndRank(COMPOSITION_YEAR_END_RANK)
                .essentialCompositionsRank(COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK)
                .build();

    }

    public static CompositionViewDTO getCompositionViewDTO() {
        return CompositionViewDTO.builder()
                .slug(COMPOSITION_SLUG)
                .title(COMPOSITION_TITLE)
                .catalogNumber(COMPOSITION_CATALOG_NUMBER_STRING)
                .musicianNickname(COMPOSITION_MUSICIAN_NICKNAME)
                .musicianSlug(COMPOSITION_MUSICIAN_SLUG)
                .albumId(COMPOSITION_ALBUM_ID)
                .feature(COMPOSITION_FEATURE)
                .year(COMPOSITION_YEAR)
                .musicGenres(COMPOSITION_MUSIC_GENRES)
                .rating(COMPOSITION_RATING)
                .yearEndRank(COMPOSITION_YEAR_END_RANK)
                .essentialCompositionsRank(COMPOSITION_ESSENTIAL_COMPOSITIONS_RANK)
                .build();
    }
}
