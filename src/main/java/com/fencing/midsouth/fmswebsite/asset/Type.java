package com.fencing.midsouth.fmswebsite.asset;

public enum Type {
    COMPETITION("Competition"),
    TRAINING("Training"),
    OTHER("Other");

    private final String value;

    public String getValue() {
        return value;
    }

    Type(String value) {
        this.value = value;
    }
}
