package com.dmitryshundrik.knowledgebase.util.enums;

import lombok.Getter;

@Getter
public enum TimelineEventType {

    MUSIC("Музыка"), LITERATURE("Литература"), PAINTING("Живопись");

    private final String label;

    TimelineEventType(String label) {
        this.label = label;
    }
}
