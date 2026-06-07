package com.ksbattleengine.model;

public class DamageCalculator {

    public static int calculateDamage(
        Pokemon attacker,
        Pokemon target,
        Move move,
        boolean critical
    ) {
        int damage = move.getPower();

        if (critical) {
            damage *= 2;
        }

        if (isStab(attacker, move)) {
            damage = (int) (damage * 1.5);
        }

        return damage;
    }

    public static boolean isStab(Pokemon attacker, Move move) {
        return attacker.getSpecies().getPrimaryType() == move.getType();
    }
}