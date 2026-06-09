package com.ksbattleengine.model;

import java.util.concurrent.ThreadLocalRandom;

public final class BattleRNG {

    private BattleRNG() {
    }

    public static boolean chance(int percentage) {
        return ThreadLocalRandom
                .current()
                .nextInt(1, 101) <= percentage;
    }

    public static boolean accuracyCheck(Move move) {
        return chance(move.getAccuracy());
    }

    public static boolean criticalCheck() {
        return chance(6);
    }

    public static double damageRandomFactor() {
        return ThreadLocalRandom
                .current()
                .nextDouble(0.85, 1.01);
    }

    public static int randomTurns(int minInclusive, int maxInclusive) {
        return ThreadLocalRandom
                .current()
                .nextInt(minInclusive, maxInclusive + 1);
    }
}