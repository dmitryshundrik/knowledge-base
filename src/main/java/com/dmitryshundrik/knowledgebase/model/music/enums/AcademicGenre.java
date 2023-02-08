package com.dmitryshundrik.knowledgebase.model.music.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum AcademicGenre {

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
    TRAGEDIE_BALLET("Трагедия-балет", "tragedie-ballet"),
    LIED("Светская песня на немецкие стихи (Lied)","lied");

    private final String label;

    private final String slug;

    AcademicGenre(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }

    public static List<AcademicGenre> getSortedValues() {
        return Arrays.stream(AcademicGenre.values()).sorted(Comparator.comparing(AcademicGenre::getLabel)).collect(Collectors.toList());
    }
}
