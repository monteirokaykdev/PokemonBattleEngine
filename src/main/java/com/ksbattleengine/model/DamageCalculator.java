package com.ksbattleengine.model;

import com.ksbattleengine.enums.MoveCategory;
import com.ksbattleengine.enums.Status;

public class DamageCalculator {

    public static int calculateDamage(
        Pokemon attacker,
        Pokemon target,
        Move move,
        boolean critical
    ) {
        int level = attacker.getLevel();
        int power = move.getPower();

        int attack = getAttackStat(attacker, move);
        int defense = getDefenseStat(target, move);

        double damage =
            (((2 * level / 5.0 + 2) * power * attack / defense) / 50) + 2;

        
        double modifier = 1.0;

        if (critical) {
            modifier *= 2;
        }

        if (isStab(attacker, move)) {
            modifier *= 1.5;
        }

        if (attacker.getStatus() == Status.BURN &&move.getCategory() == MoveCategory.PHYSICAL) {
            attack /= 2;
        }

        double typeEffectiveness =
        TypeChart.getTypeEffectiveness(
            move.getType(),
            target.getSpecies()
        );

        if (typeEffectiveness == 0.0) {
        return 0;
        }

        if (move.getCategory() == MoveCategory.STATUS) {
        return 0;
        }

        modifier *= typeEffectiveness;

        modifier *= BattleRNG.damageRandomFactor();

        return Math.max(1, (int) (damage * modifier));
    }

    public static boolean isStab(Pokemon attacker, Move move) {
        return attacker.getSpecies().getPrimaryType() == move.getType();
    }

    private static int getAttackStat(Pokemon attacker, Move move) {
        if (move.getCategory() == MoveCategory.PHYSICAL) {
            return attacker.getSpecies().getBaseStats().getAttack();
        }

        if (move.getCategory() == MoveCategory.SPECIAL) {
            return attacker.getSpecies().getBaseStats().getSpecialAttack();
        }

        return 0;
    }

    private static int getDefenseStat(Pokemon target, Move move) {
        if (move.getCategory() == MoveCategory.PHYSICAL) {
            return target.getSpecies().getBaseStats().getDefense();
        }

        if (move.getCategory() == MoveCategory.SPECIAL) {
            return target.getSpecies().getBaseStats().getSpecialDefense();
        }

        return 1;
    }

	public static int calculateSelfDamage(Pokemon pokemon) {
        int level = pokemon.getLevel();
        int hp = pokemon.getSpecies().getBaseStats().getHp();

        double damage = (((2 * level / 5.0 + 2) * hp * 0.5) / 50) + 2;

        return Math.max(1, (int) damage);
	}
}