package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;

import java.time.LocalDate;
import java.util.List;

public class Composition {

    private String title;

    private String catalogNumber;

    private Musician musician;

    private LocalDate date;

    private Period period;

    private List<Genre> genre;

    private String description;

    private List<Lyric> lyrics;

    private Double rating;

    private List<String> highlights;

}
