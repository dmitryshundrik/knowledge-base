package com.dmitryshundrik.knowledgebase.model.music.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum ContemporaryGenre {

    AFROFUTURISM("Афрофутуризм", "afrofuturism"),
    ALTERNATIVE_POP("Альтернативная поп-музыка", "alternative-pop"),
    ALTERNATIVE_REGGAETON("Альтернативный реггетон", "alternative-reggaeton"),
    ALTERNATIVE_RHYTHM_AND_BLUES("Альтернативный ритм-н-блюз", "alternative-rhythm-and-blues"),
    AMBIENT("Эмбиент", "ambient"),
    AMERICANA("Американа", "americana"),
    ART_POP("Арт-поп", "art-pop"),
    ART_ROCK("Арт-рок", "art-rock"),
    AVANT_GARDE("Авангардная музыка", "avant-garde"),
    AVANT_GARDE_JAZZ("Авангардный джаз", "avant-garde-jazz"),
    CHAMBER_POP("Чеймбер-поп", "chamber-pop"),
    COUNTRY("Кантри", "country"),
    COUNTRY_POP("Кантри-поп", "countr-pop"),

    DISCO("Диско", "disco"),
    DREAM_POP("Дрим-поп", "dream-pop"),
    ELECTROPOP("Электропоп", "electropop"),
    ETHNIC_ELECTRONICA("Этно-электроника", "ethnic-electronica"),
    EXPERIMENTAL("Экспериментальная музыка", "experimental"),
    FOLK("Фолк", "folk"),
    FOLKTRONICA("Фолктроника", "folktronica"),
    FREE_JAZZ("Фри-джаз", "free-jazz"),
    HARDCORE_PUNK("Хардкор-панк", "hardcore-punk"),

    HIP_HOP_RAP("Хип-хоп/Рэп", "hip-hop-rap"),
    INSTRUMENTAL_HIP_HOP("Инструментальный хип-хоп", "instrumental-hip-hop"),
    UNDERGROUND_HIP_HOP("Андеграунд хип-хоп", "underground-hip-hop"),
    RAP("Рэп", "rap"),
    RAP_ROCK("Рэп-рок", "rap-rock"),
    TRAP("Трэп", "trap"),
    LATIN_TRAP("Латинский трэп", "latin-trap"),
    GRIME("Грайм", "grime"),

    ELECTRONIC("Электронная музыка", "electronic"),
    INSTRUMENTAL_ELECTRONIC("Инструментальная электроника","instrumental-electronic"),
    HOUSE("Хаус", "house"),
    DOWNTEMPO("Даунтемпо", "downtempo"),
    TECHNO("Техно", "techno"),
    DUBSTEP("Дабстеп", "dubstep"),
    DANCE("Танцевальная музыка", "dance-music"),
    TRANCE("Транс", "trance"),
    DRUM_AND_BASS("Драм-н-бейс", "drum-and-bass"),
    GLITCH("Глитч", "glitch"),
    CHIPTUNE("",""),
    CHILLWAVE("",""),
    EDM("",""),


    INDUSTRIAL("Индастриал", "industrial"),
    JAZZ("Джаз", "jazz"),
    MELODIC_RAP("Мелодичный рэп", "melodic-rap"),
    MINIMAL("Минимализм", "minimal"),
    NEW_AGE("Нью-эйдж", "new-age"),
    NOISE("Нойз", "noise"),
    OLDSCHOOL_REGGAETON("Олд-скул реггетон", "old-school-reggaeton"),
    POP("Поп", "pop"),
    POP_ROCK("Поп-рок", "pop-rock"),

    RHYTHM_AND_BLUES("Ритм-н-блюз", "rhythm-and-blues"),
    ROCK("Рок", "rock"),
    SHOEGASE("Шугейз", "shoegaze"),
    SINGER_SONGWRITER("Автор-исполнитель", "singer-songwriter"),
    SPOKEN_WORD("Художественная декламация", "spoken-word");

    private final String label;

    private final String slug;

    ContemporaryGenre(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }

    public static List<ContemporaryGenre> getSortedValues() {
        return Arrays.stream(ContemporaryGenre.values()).sorted(Comparator.comparing(ContemporaryGenre::getLabel)).collect(Collectors.toList());
    }
}
