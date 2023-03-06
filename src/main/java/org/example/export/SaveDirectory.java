package org.example.export;

public enum SaveDirectory {
    AGGREGATION("src/main/resources/aggregation-pipeline-operations/"),
    FILTER("src/main/resources/filters-operations/"),
    UPDATE("src/main/resources/update-operations/");

    private String path;
    private SaveDirectory(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }
}
