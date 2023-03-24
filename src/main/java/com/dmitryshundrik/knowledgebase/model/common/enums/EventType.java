package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum EventType {

    ENTITY_TIMELINE("История сущности"), MUSIC_TIMELINE("История музыки");

    private final String label;

    EventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
