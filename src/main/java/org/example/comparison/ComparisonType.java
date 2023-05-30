package org.example.comparison;

public enum ComparisonType {

    AUTO("Автоматическая оценка"),
    MANUAL("Ручная оценка");

    private String title;

    ComparisonType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
