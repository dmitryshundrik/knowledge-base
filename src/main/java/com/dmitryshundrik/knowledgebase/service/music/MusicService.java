package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class MusicService {

    private final static Map<String, Genre> slugGenreMap = new HashMap<>();

    private final static Map<String, Period> slugPeriodMap = new HashMap<>();


    static {
        for (Genre value : Genre.values()) {
            slugGenreMap.put(value.getSlug(), value);
        }

        for (Period value : Period.values()) {
            slugPeriodMap.put(value.getSlug(), value);
        }

    }

    public Period getPeriodBySlug(String slug) {
        return slugPeriodMap.get(slug);
    }

    public Genre getGenreBySlug(String slug) {
        return slugGenreMap.get(slug);
    }


}
