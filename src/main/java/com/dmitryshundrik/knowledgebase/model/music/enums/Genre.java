package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Genre {

    // Instrumental Genres
    CONCERTO("Концерт"),
    FUGUE("Фуга"),
    SUITE("Сюита"),
    SONATA("Соната"),
    OVERTURE("Увертюра"),
    PARTITA("Партита"),
    CANZONE("Канцона"),
    FANTASIA("Фантазия"),
    RICERCAR("Ричеркар"),
    TOCCATA("Токката"),
    PRELUDE("Прелюдия"),
    CHACONNE("Чакона"),
    PASSACAGLIA("Пассакалья"),
    CONCERTO_GROSSO("Кончерто гроссо"),

    //Vocal Genres
    ARIA("Ария"),
    CANTATA("Кантата"),
    OPERA("Опера"),
    OPERA_BALLET("Опера-балет"),
    PASTORALE("Пастораль"),
    BALLET("Балет"),
    COMEDIE("Комедия"),
    COMEDIE_BALLET("Комедия-балет"),
    TRAGEIE_BALLET("Трагедия-балет"),
    DIVERTISSEMENT("Дивертисмент"),

    PASSION("Страсти"),
    ORATORIO("Оратория"),
    MADRIGAL("Мадригал"),
    MASS("Месса"),
    MOTET("Мотет"),
    GRAND_MOTET("Большой мотет"),
    PETIT_MOTET("Малый мотет"),
    CHORALE("Хорал");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
