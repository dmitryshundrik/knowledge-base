package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum Country {

    RUSSIA("Россия"),
    FRANCE("Франция"),
    ITALY("Италия"),
    UK("Великобритания"),
    JAPAN("Япония"),
    BELGIUM("Бельгия"),
    BELARUS("Беларусь"),
    CHINA("Китай"),
    INDIA("Индия");

    private final String label;



    Country(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
