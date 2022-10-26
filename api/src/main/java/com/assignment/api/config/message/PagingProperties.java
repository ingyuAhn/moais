package com.assignment.api.config.message;

public enum PagingProperties {
    CREATE_DATE("createDate"),
    UPDATE_DATE("updateDate");

    private final String properties;

    PagingProperties(String properties) {
        this.properties = properties;
    }

    public String getProperties() {
        return properties;
    }
}
