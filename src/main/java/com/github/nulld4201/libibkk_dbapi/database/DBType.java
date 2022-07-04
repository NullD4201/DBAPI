package com.github.nulld4201.libibkk_dbapi.database;

public enum DBType {
    DEV("Dev", "개발 모드"),
    RELEASE("Release", "서버 모드");

    private final String typeName;
    private final String description;
    DBType(String typeName, String description) {
        this.typeName = typeName;
        this.description = description;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getTypeDescription() {
        return description;
    }
}
