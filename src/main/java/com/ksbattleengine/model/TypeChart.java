package com.ksbattleengine.model;

import java.util.HashMap;
import java.util.Map;

import com.ksbattleengine.enums.PokemonType;

public class TypeChart {
    private static final double NO_EFFECT = 0.0;
    private static final double NOT_VERY_EFFECTIVE = 0.5;
    private static final double NORMAL_EFFECTIVE = 1.0;
    private static final double SUPER_EFFECTIVE = 2.0;


    /*
    HashMap structure:
    FIRE(AttackerType) {

        (DefenderType, Multiplier)
        GRASS: 2.0,
        WATER: 0.5,
        FIRE: 0.5
    },
    */
    private static final Map<PokemonType,
                             Map<PokemonType, Double>> TYPE_CHART = 
                             new HashMap<>();

    
    static {
        set(PokemonType.FIRE, PokemonType.GRASS, SUPER_EFFECTIVE);
        set(PokemonType.FIRE, PokemonType.WATER, NOT_VERY_EFFECTIVE);
        set(PokemonType.FIRE, PokemonType.FIRE, NOT_VERY_EFFECTIVE);

        // WATER
        set(PokemonType.WATER, PokemonType.FIRE, SUPER_EFFECTIVE);
        set(PokemonType.WATER, PokemonType.GRASS, NOT_VERY_EFFECTIVE);
        set(PokemonType.WATER, PokemonType.WATER, NOT_VERY_EFFECTIVE);

        // GRASS
        set(PokemonType.GRASS, PokemonType.WATER, SUPER_EFFECTIVE);
        set(PokemonType.GRASS, PokemonType.FIRE, NOT_VERY_EFFECTIVE);
        set(PokemonType.GRASS, PokemonType.GRASS, NOT_VERY_EFFECTIVE);
        }
        
    private static void set(PokemonType attackType, PokemonType defenseType, double multiplier) {
        TYPE_CHART.computeIfAbsent(attackType, k -> new HashMap<>()).put(defenseType, multiplier);
    }

    public static double getTypeEffectiveness(PokemonType attackType, PokemonType defenseType) {
        return TYPE_CHART.getOrDefault(attackType, Map.of()).getOrDefault(defenseType, NORMAL_EFFECTIVE);
    }
    
}
