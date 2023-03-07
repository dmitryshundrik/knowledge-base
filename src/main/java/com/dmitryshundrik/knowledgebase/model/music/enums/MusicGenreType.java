package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum MusicGenreType {

    CLASSICAL("Классический"), CONTEMPORARY("Современный");

    private final String label;

    MusicGenreType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
