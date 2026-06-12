package com.ksbattleengine.model;

import com.ksbattleengine.enums.StatType;

import java.util.EnumMap;
import java.util.Map;

public class StatStage {

    private static final int MIN_STAGE = -6;
    private static final int MAX_STAGE = 6;

    private final Map<StatType, Integer> stages =
        new EnumMap<>(StatType.class);

    public StatStage() {
        for (StatType stat : StatType.values()) {
            stages.put(stat, 0);
        }
    }

    public int getStage(StatType stat) {
        return stages.get(stat);
    }

    public void changeStage(StatType stat, int amount) {
        int current = stages.get(stat);

        int newStage = Math.max(
            MIN_STAGE,
            Math.min(MAX_STAGE, current + amount)
        );

        stages.put(stat, newStage);
    }

    public double getMultiplier(StatType stat) {
        int stage = getStage(stat);

        if (stage >= 0) {
            return (2.0 + stage) / 2.0;
        }

        return 2.0 / (2.0 - stage);
    }
}