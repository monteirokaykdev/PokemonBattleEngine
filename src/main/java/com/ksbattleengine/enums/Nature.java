package com.ksbattleengine.enums;

public enum Nature {

    HARDY(null, null),
    LONELY(StatType.ATTACK, StatType.DEFENSE),
    BRAVE(StatType.ATTACK, StatType.SPEED),
    ADAMANT(StatType.ATTACK, StatType.SPECIAL_ATTACK),
    NAUGHTY(StatType.ATTACK, StatType.SPECIAL_DEFENSE),

    BOLD(StatType.DEFENSE, StatType.ATTACK),
    DOCILE(null, null),
    RELAXED(StatType.DEFENSE, StatType.SPEED),
    IMPISH(StatType.DEFENSE, StatType.SPECIAL_ATTACK),
    LAX(StatType.DEFENSE, StatType.SPECIAL_DEFENSE),

    TIMID(StatType.SPEED, StatType.ATTACK),
    HASTY(StatType.SPEED, StatType.DEFENSE),
    SERIOUS(null, null),
    JOLLY(StatType.SPEED, StatType.SPECIAL_ATTACK),
    NAIVE(StatType.SPEED, StatType.SPECIAL_DEFENSE),

    MODEST(StatType.SPECIAL_ATTACK, StatType.ATTACK),
    MILD(StatType.SPECIAL_ATTACK, StatType.DEFENSE),
    QUIET(StatType.SPECIAL_ATTACK, StatType.SPEED),
    BASHFUL(null, null),
    RASH(StatType.SPECIAL_ATTACK, StatType.SPECIAL_DEFENSE),

    CALM(StatType.SPECIAL_DEFENSE, StatType.ATTACK),
    GENTLE(StatType.SPECIAL_DEFENSE, StatType.DEFENSE),
    SASSY(StatType.SPECIAL_DEFENSE, StatType.SPEED),
    CAREFUL(StatType.SPECIAL_DEFENSE, StatType.SPECIAL_ATTACK),
    QUIRKY(null, null);

    private final StatType increasedStat;
    private final StatType decreasedStat;

    Nature(StatType increasedStat, StatType decreasedStat) {
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
    }

    public double getMultiplier(StatType stat) {
        if (boosts(stat)) {
            return 1.1;
        }

        if (lowers(stat)) {
            return 0.9;
        }

        return 1.0;
    }

    public boolean boosts(StatType stat) {
        return stat != null && stat == increasedStat;
    }

    public boolean lowers(StatType stat) {
        return stat != null && stat == decreasedStat;
    }

    public boolean isNeutral() {
        return increasedStat == null && decreasedStat == null;
    }
}