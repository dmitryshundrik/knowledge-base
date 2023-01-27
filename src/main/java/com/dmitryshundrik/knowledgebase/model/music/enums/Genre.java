package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Genre {

    // Modern Genres
    AFROFUTURISM("Афрофутуризм", "afrofuturism"),
    ALTERNATIVE("Альтернативная музыка", "alternative"),
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
    HARDCORE_PUNK("Хардкор-панк", "hardcore-punk"),
    HIP_HOP("Хип-хоп", "hip-hop"),
    JAZZ("Джаз", "jazz"),
    MELODIC_RAP("Мелодичный рэп", "melodic-rap"),
    MINIMAL("Минимализм", "minimal"),
    NEW_AGE("Нью-эйдж", "new-age"),
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
