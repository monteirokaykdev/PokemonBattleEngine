package com.ksbattleengine.model;

import java.util.HashMap;
import java.util.Map;

import com.ksbattleengine.enums.PokemonType;

public class TypeChart {

    private static final double NO_EFFECT = 0.0;
    private static final double NOT_VERY_EFFECTIVE = 0.5;
    private static final double NORMAL_EFFECTIVE = 1.0;
    private static final double SUPER_EFFECTIVE = 2.0;

    private static final Map<PokemonType, Map<PokemonType, Double>> TYPE_CHART =
            new HashMap<>();

    static {
        notVeryEffective(PokemonType.NORMAL, PokemonType.ROCK, PokemonType.STEEL);
        noEffect(PokemonType.NORMAL, PokemonType.GHOST);

        superEffective(PokemonType.FIRE, PokemonType.GRASS, PokemonType.ICE, PokemonType.BUG, PokemonType.STEEL);
        notVeryEffective(PokemonType.FIRE, PokemonType.FIRE, PokemonType.WATER, PokemonType.ROCK, PokemonType.DRAGON);

        superEffective(PokemonType.WATER, PokemonType.FIRE, PokemonType.GROUND, PokemonType.ROCK);
        notVeryEffective(PokemonType.WATER, PokemonType.WATER, PokemonType.GRASS, PokemonType.DRAGON);

        superEffective(PokemonType.GRASS, PokemonType.WATER, PokemonType.GROUND, PokemonType.ROCK);
        notVeryEffective(PokemonType.GRASS, PokemonType.FIRE, PokemonType.GRASS, PokemonType.POISON,
                PokemonType.FLYING, PokemonType.BUG, PokemonType.DRAGON, PokemonType.STEEL);

        superEffective(PokemonType.ELECTRIC, PokemonType.WATER, PokemonType.FLYING);
        notVeryEffective(PokemonType.ELECTRIC, PokemonType.ELECTRIC, PokemonType.GRASS, PokemonType.DRAGON);
        noEffect(PokemonType.ELECTRIC, PokemonType.GROUND);

        superEffective(PokemonType.ICE, PokemonType.GRASS, PokemonType.GROUND, PokemonType.FLYING, PokemonType.DRAGON);
        notVeryEffective(PokemonType.ICE, PokemonType.FIRE, PokemonType.WATER, PokemonType.ICE, PokemonType.STEEL);

        superEffective(PokemonType.FIGHTING, PokemonType.NORMAL, PokemonType.ICE, PokemonType.ROCK,
                PokemonType.DARK, PokemonType.STEEL);
        notVeryEffective(PokemonType.FIGHTING, PokemonType.POISON, PokemonType.FLYING, PokemonType.PSYCHIC,
                PokemonType.BUG, PokemonType.FAIRY);
        noEffect(PokemonType.FIGHTING, PokemonType.GHOST);

        superEffective(PokemonType.POISON, PokemonType.GRASS, PokemonType.FAIRY);
        notVeryEffective(PokemonType.POISON, PokemonType.POISON, PokemonType.GROUND, PokemonType.ROCK, PokemonType.GHOST);
        noEffect(PokemonType.POISON, PokemonType.STEEL);

        superEffective(PokemonType.GROUND, PokemonType.FIRE, PokemonType.ELECTRIC, PokemonType.POISON,
                PokemonType.ROCK, PokemonType.STEEL);
        notVeryEffective(PokemonType.GROUND, PokemonType.GRASS, PokemonType.BUG);
        noEffect(PokemonType.GROUND, PokemonType.FLYING);

        superEffective(PokemonType.FLYING, PokemonType.GRASS, PokemonType.FIGHTING, PokemonType.BUG);
        notVeryEffective(PokemonType.FLYING, PokemonType.ELECTRIC, PokemonType.ROCK, PokemonType.STEEL);

        superEffective(PokemonType.PSYCHIC, PokemonType.FIGHTING, PokemonType.POISON);
        notVeryEffective(PokemonType.PSYCHIC, PokemonType.PSYCHIC, PokemonType.STEEL);
        noEffect(PokemonType.PSYCHIC, PokemonType.DARK);

        superEffective(PokemonType.BUG, PokemonType.GRASS, PokemonType.PSYCHIC, PokemonType.DARK);
        notVeryEffective(PokemonType.BUG, PokemonType.FIRE, PokemonType.FIGHTING, PokemonType.POISON,
                PokemonType.FLYING, PokemonType.GHOST, PokemonType.STEEL, PokemonType.FAIRY);

        superEffective(PokemonType.ROCK, PokemonType.FIRE, PokemonType.ICE, PokemonType.FLYING, PokemonType.BUG);
        notVeryEffective(PokemonType.ROCK, PokemonType.FIGHTING, PokemonType.GROUND, PokemonType.STEEL);

        superEffective(PokemonType.GHOST, PokemonType.PSYCHIC, PokemonType.GHOST);
        notVeryEffective(PokemonType.GHOST, PokemonType.DARK);
        noEffect(PokemonType.GHOST, PokemonType.NORMAL);

        superEffective(PokemonType.DRAGON, PokemonType.DRAGON);
        notVeryEffective(PokemonType.DRAGON, PokemonType.STEEL);
        noEffect(PokemonType.DRAGON, PokemonType.FAIRY);

        superEffective(PokemonType.DARK, PokemonType.PSYCHIC, PokemonType.GHOST);
        notVeryEffective(PokemonType.DARK, PokemonType.FIGHTING, PokemonType.DARK, PokemonType.FAIRY);

        superEffective(PokemonType.STEEL, PokemonType.ICE, PokemonType.ROCK, PokemonType.FAIRY);
        notVeryEffective(PokemonType.STEEL, PokemonType.FIRE, PokemonType.WATER, PokemonType.ELECTRIC, PokemonType.STEEL);

        superEffective(PokemonType.FAIRY, PokemonType.FIGHTING, PokemonType.DRAGON, PokemonType.DARK);
        notVeryEffective(PokemonType.FAIRY, PokemonType.FIRE, PokemonType.POISON, PokemonType.STEEL);
    }

    private static void set(PokemonType attackType, PokemonType defenseType, double multiplier) {
        TYPE_CHART
                .computeIfAbsent(attackType, key -> new HashMap<>())
                .put(defenseType, multiplier);
    }

    private static void superEffective(PokemonType attackType, PokemonType... defenseTypes) {
        for (PokemonType defenseType : defenseTypes) {
            set(attackType, defenseType, SUPER_EFFECTIVE);
        }
    }

    private static void notVeryEffective(PokemonType attackType, PokemonType... defenseTypes) {
        for (PokemonType defenseType : defenseTypes) {
            set(attackType, defenseType, NOT_VERY_EFFECTIVE);
        }
    }

    private static void noEffect(PokemonType attackType, PokemonType... defenseTypes) {
        for (PokemonType defenseType : defenseTypes) {
            set(attackType, defenseType, NO_EFFECT);
        }
    }

    private static double getMultiplier(PokemonType attackType, PokemonType defenseType) {
        return TYPE_CHART
                .getOrDefault(attackType, Map.of())
                .getOrDefault(defenseType, NORMAL_EFFECTIVE);
    }

    public static double getTypeEffectiveness(PokemonType attackType, PokemonSpecies defender) {
        double multiplier = getMultiplier(attackType, defender.getPrimaryType());

        if (defender.getSecondaryType() != null) {
            multiplier *= getMultiplier(attackType, defender.getSecondaryType());
        }

        return multiplier;
    }
}