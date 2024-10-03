package com.dmitryshundrik.knowledgebase.model.music.enums;

import lombok.Getter;

@Getter
public enum MusicGenreType {

    CLASSICAL("Классический"), CONTEMPORARY("Современный");

    private final String label;

    MusicGenreType(String label) {
        this.label = label;
    }
}
