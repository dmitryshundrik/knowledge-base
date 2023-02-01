package com.dmitryshundrik.knowledgebase.model.timeline;

public enum TimelineType {
    MUSIC("Музыка");

    private final String label;

    TimelineType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
    }
