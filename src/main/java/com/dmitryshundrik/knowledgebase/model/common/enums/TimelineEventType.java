package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum TimelineEventType {

    MUSIC("Музыка"), LITERATURE("Литература"), PAINTING("Живопись");

    private final String label;

    TimelineEventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
