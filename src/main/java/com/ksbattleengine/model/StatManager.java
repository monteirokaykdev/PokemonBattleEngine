package com.ksbattleengine.model;

import com.ksbattleengine.enums.StatType;

public class StatManager {

    public static void applyStatChange(
        Pokemon attacker,
        Pokemon target,
        Move move
    ) {
        if (move.getStatChanges() == null || move.getStatChanges().isEmpty()) {
            return;
        }

        Pokemon affectedPokemon =
            move.isTargetSelf() ? attacker : target;

        for (MoveStatChange change : move.getStatChanges()) {
            StatType stat = change.getStat();
            int amount = change.getAmount();

            int oldStage =
                affectedPokemon.getStatStages().getStage(stat);

            affectedPokemon.getStatStages().changeStage(stat, amount);

            int newStage =
                affectedPokemon.getStatStages().getStage(stat);

            printStageMessage(
                affectedPokemon,
                stat,
                amount,
                oldStage,
                newStage
            );
        }
    }

    private static void printStageMessage(
        Pokemon pokemon,
        StatType stat,
        int amount,
        int oldStage,
        int newStage
    ) {
        String statName = formatStatName(stat);

        if (oldStage == newStage) {
            if (amount > 0) {
                System.out.println(
                    pokemon.getSpecies().getName()
                    + "'s "
                    + statName
                    + " won't go any higher!"
                );
            } else {
                System.out.println(
                    pokemon.getSpecies().getName()
                    + "'s "
                    + statName
                    + " can't go any lower!"
                );
            }

            return;
        }

        int actualChange = Math.abs(newStage - oldStage);

        if (amount > 0) {
            printBoostMessage(pokemon, statName, actualChange, newStage);
        } else {
            printDropMessage(pokemon, statName, actualChange);
        }
    }

    private static void printBoostMessage(
        Pokemon pokemon,
        String statName,
        int actualChange,
        int newStage
    ) {
        if (newStage == 6) {
            System.out.println(
                "The mysterious power raised "
                + pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " to its highest level!"
            );
            return;
        }

        if (actualChange == 1) {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " rose!"
            );
        } else if (actualChange == 2) {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " sharply rose!"
            );
        } else {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " rose drastically!"
            );
        }
    }

    private static void printDropMessage(
        Pokemon pokemon,
        String statName,
        int actualChange
    ) {
        if (actualChange == 1) {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " fell!"
            );
        } else if (actualChange == 2) {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " harshly fell!"
            );
        } else {
            System.out.println(
                pokemon.getSpecies().getName()
                + "'s "
                + statName
                + " severely fell!"
            );
        }
    }

    private static String formatStatName(StatType stat) {
        return switch (stat) {
            case ATTACK -> "Attack";
            case DEFENSE -> "Defense";
            case SPECIAL_ATTACK -> "Special Attack";
            case SPECIAL_DEFENSE -> "Special Defense";
            case SPEED -> "Speed";
        };
    }
}