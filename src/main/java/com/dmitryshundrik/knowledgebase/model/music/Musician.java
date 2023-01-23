package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;

import java.time.LocalDate;
import java.util.List;

public class Musician {

    private String firstName;

    private String lastName;

    private LocalDate born;

    private LocalDate died;

    private Period period;

    private String image;

    private List<Event> biography;

    private List<Composition> compositions;

}
