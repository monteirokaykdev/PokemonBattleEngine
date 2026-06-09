package com.ksbattleengine.enums;

public enum Status {
    NONE(""),
    BURN("burned"),
    FREEZE("frozen"),
    PARALYSIS("paralyzed"),
    POISON("poisoned"),
    BADLY_POISON("badly poisoned"),
    SLEEP("asleep"),
    CONFUSION("confused");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}