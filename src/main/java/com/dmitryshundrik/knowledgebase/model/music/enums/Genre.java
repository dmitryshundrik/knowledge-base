package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Genre {

    // Modern Genres
    NEW_AGE("Нью-эйдж", "new-age"),
    ETHNIC_ELECTRONICA("Этно-электроника", "ethnic-electronica"),
    AVANT_GARDE("Авангардная музыка", "avant-garde"),
    AMERICANA("Американа", "americana"),
    DREAM_POP("Дрим-поп", "dream-pop"),
    CHAMBER_POP("Чеймбер-поп", "chamber-pop"),
    HIP_HOP("Хип-хоп", "hip-hop"),
    RAP("Рэп", "rap"),
    RHYTHM_AND_BLUES("Ритм-н-блюз", "rhythm-and-blues"),
    DISCO("Диско", "disco"),
    MELODIC_RAP("Мелодичный рэп", "melodic-rap"),
    HARDCORE_PUNK("Хардкор-панк", "hardcore-punk"),
    RAP_ROCK("Рэп-рок", "rap-rock"),
    COUNTRY("Кантри", "country"),
    ALTERNATIVE_REGGAETON("Альтернативный реггетон", "alternative-reggaeton"),
    EXPERIMENTAL("Экспериментальная музыка", "experimental"),
    OLDSCHOOL_REGGAETON("Олд-скул реггетон", "old-school-reggaeton"),
    ELECTRONIC("Электронная музыка", "electronic"),
    AVANT_GARDE_JAZZ("Авангардный джаз", "avant-garde-jazz"),
    SPOKEN_WORD("Художественная декламация", "spoken-word"),
    FOLKTRONICA("Фолктроника", "folktronica"),
    ELECTROPOP("Электропоп", "electropop"),
    DANCE("Танцевальная музыка", "dance-music"),
    ALTERNATIVE_RHYTHM_AND_BLUES("Альтернативный ритм-н-блюз", "alternative-rhythm-and-blues"),
    FREE_JAZZ("Фри-джаз", "free-jazz"),
    AFROFUTURISM("Афрофутуризм", "afrofuturism"),
    MINIMAL("Минимализм", "minimal"),
    TECHNO("Техно", "techno"),
    ALTERNATIVE_POP("Альтернативная поп-музыка", "alternative-pop"),

    ART_ROCK("Арт-рок","art-rock"),
    ART_POP("Арт-поп","art-pop"),

    // Instrumental Genres
    CANZONE("Канцона", "canzone"),
    CHACONNE("Чакона", "chaconne"),
    CONCERTO("Концерт", "concerto"),
    CONCERTO_GROSSO("Кончерто гроссо", "concerto-grosso"),
    FANTASIA("Фантазия", "fantasia"),
    FUGUE("Фуга", "fugue"),
    OVERTURE("Увертюра", "overture"),
    PARTITA("Партита", "partita"),
    PASSACAGLIA("Пассакалья", "passacaglia"),
    PRELUDE("Прелюдия", "prelude"),
    RICERCAR("Ричеркар", "ricercar"),
    SONATA("Соната", "sonata"),
    SUITE("Сюита", "suite"),
    TOCCATA("Токката", "toccata"),


    //Vocal Genres
    ARIA("Ария", "aria"),
    BALLET("Балет", "ballet"),
    CANTATA("Кантата", "cantata"),
    CHORALE("Хорал", "chorale"),
    COMEDIE("Комедия", "comedie"),
    COMEDIE_BALLET("Комедия-балет", "comedie-ballet"),
    DIVERTISSEMENT("Дивертисмент", "divertissement"),
    GRAND_MOTET("Большой мотет", "grand-motet"),
    MADRIGAL("Мадригал", "madrigal"),
    MASS("Месса", "mass"),
    MOTET("Мотет", "motet"),
    OPERA("Опера", "opera"),
    OPERA_BALLET("Опера-балет", "opera-ballet"),
    ORATORIO("Оратория", "oratorio"),
    PASSION("Страсти", "passion"),
    PASTORALE("Пастораль", "pastorale"),
    PETIT_MOTET("Малый мотет", "petit-motet"),
    TRAGEDIE_BALLET("Трагедия-балет", "tragedie-ballet");

    private final String label;

    private final String slug;

    Genre(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }
}
