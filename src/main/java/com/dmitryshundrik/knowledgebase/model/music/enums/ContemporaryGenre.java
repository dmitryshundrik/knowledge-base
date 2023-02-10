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
    DANCE("Танцевальная музыка", "dance-music"),
    DISCO("Диско", "disco"),
    DREAM_POP("Дрим-поп", "dream-pop"),
    ELECTRONIC("Электронная музыка", "electronic"),
    ELECTROPOP("Электропоп", "electropop"),
    ETHNIC_ELECTRONICA("Этно-электроника", "ethnic-electronica"),
    EXPERIMENTAL("Экспериментальная музыка", "experimental"),
    FOLK("Фолк", "folk"),
    FOLKTRONICA("Фолктроника", "folktronica"),
    FREE_JAZZ("Фри-джаз", "free-jazz"),
    GLITCH("Глитч","glitch"),
    HARDCORE_PUNK("Хардкор-панк", "hardcore-punk"),
    HIP_HOP("Хип-хоп", "hip-hop"),
    INDUSTRIAL("Индастриал","industrial"),
    JAZZ("Джаз", "jazz"),
    MELODIC_RAP("Мелодичный рэп", "melodic-rap"),
    MINIMAL("Минимализм", "minimal"),
    NEW_AGE("Нью-эйдж", "new-age"),
    NOISE("Нойз","noise"),
    OLDSCHOOL_REGGAETON("Олд-скул реггетон", "old-school-reggaeton"),
    POP("Поп", "pop"),
    POP_ROCK("Поп-рок", "pop-rock"),
    RAP("Рэп", "rap"),
    RAP_ROCK("Рэп-рок", "rap-rock"),
    RHYTHM_AND_BLUES("Ритм-н-блюз", "rhythm-and-blues"),
    ROCK("Рок", "rock"),
    SHOEGASE("Шугейз", "shoegaze"),
    SINGER_SONGWRITER("Автор-исполнитель", "singer-songwriter"),
    SPOKEN_WORD("Художественная декламация", "spoken-word"),
    TECHNO("Техно", "techno"),
    TRAP("Трэп","trap"),
    LATIN_TRAP("Латинский трэп", "latin-trap");

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
