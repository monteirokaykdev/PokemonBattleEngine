package com.ksbattleengine.model;

import com.ksbattleengine.enums.PokemonType;
import com.ksbattleengine.enums.Status;

public class StatusManager {

    private static final int MIN_TURNS_CONFUSED = 2;
    private static final int MAX_TURNS_CONFUSED = 5;
    private static final int MIN_TURNS_SLEEP = 1;
    private static final int MAX_TURNS_SLEEP = 3;


    public static void applyStatusEffect(Pokemon target, Move move) {
        if (move.getStatusEffect() == Status.NONE) {
            return;
        }

        if (!BattleRNG.chance(move.getStatusChance())) {
        return;
        }

        if (move.getStatusEffect() == Status.CONFUSION) {
            if (target.isConfused()) {
                System.out.println(
                    target.getSpecies().getName()
                    + " is already confused!"
                );
                return;
            }

            target.setConfused(true);

            target.setConfusionTurns(
                BattleRNG.randomTurns(
                    MIN_TURNS_CONFUSED,
                    MAX_TURNS_CONFUSED
                )
            );

            System.out.println(
                target.getSpecies().getName()
                + " became confused!"
            );

            return;
        }

        if (target.getStatus() != Status.NONE) {
            System.out.println(
                target.getSpecies().getName()
                + " already has a status condition!"
            );
            return;
        }

        if (!canApplyStatus(target, move)) {
            System.out.println(
                "It doesn't affect "
                + target.getSpecies().getName()
                + "!"
            );
            return;
        }

        target.setStatus(move.getStatusEffect());

        if(move.getStatusEffect() == Status.SLEEP){
            target.setSleepTurns(
            BattleRNG.randomTurns(MIN_TURNS_SLEEP, MAX_TURNS_SLEEP)
            );
        }

        System.out.println(
            target.getSpecies().getName()
            + " was "
            + move.getStatusEffect().getMessage()
            + "!"
        );

        if(move.getStatusEffect() == Status.BADLY_POISON){
            target.setToxicCounter(1);
        }
    }

    public static void applyTurnEndStatusEffects(Pokemon pokemon) {
        if (pokemon.isFainted()) {
            return;
        }

        if (pokemon.getStatus() == Status.BURN) {
            int burnDamage = Math.max(
                1,
                pokemon.getSpecies().getBaseStats().getHp() / 16
            );

            pokemon.takeDamage(burnDamage);

            System.out.println(
                pokemon.getSpecies().getName()
                + " is hurt by burn! Lost "
                + burnDamage
                + " HP."
            );

            System.out.println(
                pokemon.getSpecies().getName()
                + " HP: "
                + pokemon.getCurrentHP()
            );
        }

        if (pokemon.getStatus() == Status.POISON) {
            int poisonDamage = Math.max(
                1,
                pokemon.getSpecies().getBaseStats().getHp() / 8
            );

            pokemon.takeDamage(poisonDamage);

            System.out.println(
                pokemon.getSpecies().getName()
                + " is hurt by poison! Lost "
                + poisonDamage
                + " HP."
            );

            System.out.println(
                pokemon.getSpecies().getName()
                + " HP: "
                + pokemon.getCurrentHP()
            );
        }

        if (pokemon.getStatus() == Status.BADLY_POISON) {
            int toxicDamage = Math.max(
                1,
                (pokemon.getSpecies().getBaseStats().getHp() / 16) * pokemon.getToxicCounter()
            );

            pokemon.takeDamage(toxicDamage);

            pokemon.setToxicCounter(pokemon.getToxicCounter() + 1);

            System.out.println(
                pokemon.getSpecies().getName()
                + " is hurt by toxic! Lost "
                + toxicDamage
                + " HP."
            );

            System.out.println(
                pokemon.getSpecies().getName()
                + " HP: "
                + pokemon.getCurrentHP()
            );
        }
    }

    public static boolean canAct(Pokemon pokemon) {
        if (pokemon.getStatus() == Status.FREEZE) {
            if (BattleRNG.chance(20)) {
                pokemon.setStatus(Status.NONE);
                System.out.println(pokemon.getSpecies().getName() + " thawed out!");
                return true;
            }

            System.out.println(pokemon.getSpecies().getName() + " is frozen solid and can't move!");
            return false;
        }

        if (pokemon.getStatus() == Status.PARALYSIS) {
            if (BattleRNG.chance(25)) {
                System.out.println(pokemon.getSpecies().getName() + " is paralyzed and can't move!");
                return false;
            }
        }
        if (pokemon.getStatus() == Status.SLEEP) {
            pokemon.setSleepTurns(
                pokemon.getSleepTurns() - 1
            );

            if (pokemon.getSleepTurns() <= 0) {
                pokemon.setStatus(Status.NONE);

                System.out.println(
                    pokemon.getSpecies().getName()
                    + " woke up!"
                );

                return true;
            }

            System.out.println(
                pokemon.getSpecies().getName()
                + " is asleep and can't move!"
            );

            return false;
        }
        if (pokemon.isConfused()) {
            pokemon.setConfusionTurns(
                pokemon.getConfusionTurns() - 1
            );

            if (pokemon.getConfusionTurns() <= 0) {
                pokemon.setConfused(false);

                System.out.println(
                    pokemon.getSpecies().getName()
                    + " snapped out of confusion!"
                );

                return true;
            }

            if (BattleRNG.chance(33)) {
                System.out.println(
                    pokemon.getSpecies().getName()
                    + " hurt itself in its confusion!"
                );

                int selfDamage =
                    DamageCalculator.calculateSelfDamage(pokemon);

                pokemon.takeDamage(selfDamage);

                System.out.println(
                    pokemon.getSpecies().getName()
                    + " lost "
                    + selfDamage
                    + " HP! Current HP: "
                    + pokemon.getCurrentHP()
                );

                return false;
            }

            System.out.println(
                pokemon.getSpecies().getName()
                + " is confused but managed to act!"
            );
        }

        return true;
    }

    private static boolean canApplyStatus(Pokemon target, Move move) {
        Status effect = move.getStatusEffect();

        if (effect == Status.NONE) {
            return false;
        }

        PokemonType primary = target.getSpecies().getPrimaryType();
        PokemonType secondary = target.getSpecies().getSecondaryType();

        if (effect == Status.BURN) {
            return primary != PokemonType.FIRE &&
                   secondary != PokemonType.FIRE;
        }

        if (effect == Status.POISON || effect == Status.BADLY_POISON) {
            return primary != PokemonType.POISON &&
                   secondary != PokemonType.POISON &&
                   primary != PokemonType.STEEL &&
                   secondary != PokemonType.STEEL;
        }

        if (effect == Status.PARALYSIS &&
            move.getType() == PokemonType.ELECTRIC) {
            return primary != PokemonType.GROUND &&
                   secondary != PokemonType.GROUND;
        }

        if (effect == Status.SLEEP &&
            move.getType() == PokemonType.GRASS) {
            return primary != PokemonType.GRASS &&
                   secondary != PokemonType.GRASS;
        }

        return true;
    }

}