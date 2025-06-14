package com.dmitryshundrik.knowledgebase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {

    public static final String MUSICIAN_NOT_FOUND_MESSAGE = "Musician not found for slug: %s";
    public static final String ALBUM_NOT_FOUND_MESSAGE = "Album not found for ID: %s";
    public static final String COMPOSITION_NOT_FOUND_MESSAGE = "Composition not found for slug: %s";
    public static final String MUSIC_PERIOD_NOT_FOUND_MESSAGE = "Music period not found for slug: %s";
    public static final String MUSIC_GENRE_NOT_FOUND_MESSAGE = "Music genre not found for slug: %s";
    public static final String YEAR_IN_MUSIC_NOT_FOUND_MESSAGE = "Year in music not found for year: %s";

    public static final String WRITER_NOT_FOUND_MESSAGE = "Writer not found for slug: %s";
    public static final String PROSE_NOT_FOUND_MESSAGE = "Prose not found for ID: %s";
    public static final String QUOTE_NOT_FOUND_MESSAGE = "Quote not found for ID: %s";
    public static final String WORD_NOT_FOUND_MESSAGE = "Word not found for ID: %s";

    public static final String RECIPE_NOT_FOUND_MESSAGE = "Recipe not found for slug: %s";
    public static final String COCKTAIL_NOT_FOUND_MESSAGE = "Cocktail not found for slug: %s";

    public static final String ARTIST_NOT_FOUND_MESSAGE = "Artist not found for slug: %s";
    public static final String PAINTING_NOT_FOUND_MESSAGE = "Painting not found for slug: %s";

    public static final String FILM_NOT_FOUND_MESSAGE = "Film not found for slug: %s";
    public static final String CRITICS_LIST_NOT_FOUND_MESSAGE = "Critics list not found for slug: %s";

    public NotFoundException(String reason) {
        super(HttpStatus.NOT_FOUND, reason);
    }
}
