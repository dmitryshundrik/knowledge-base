package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Genre {

    RAP("Рэп"), POP("Поп"), ROCK("Рок");

    private final String label;

    Genre(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
