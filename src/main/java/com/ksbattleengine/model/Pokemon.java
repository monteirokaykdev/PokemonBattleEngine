package com.ksbattleengine.model;

import com.ksbattleengine.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Pokemon {
    private PokemonSpecies species;
    private int level;
    private int currentHP;
    private Status status;
    private boolean confused;
    private int confusionTurns;
    private int sleepTurns;
    private int toxicCounter;

    public Pokemon(PokemonSpecies species, int level) {
    this.species = species;
    this.level = level;
    this.currentHP = species.getBaseStats().getHp();
    this.status = Status.NONE;
    this.confused = false;
    this.sleepTurns = 0;
    this.confusionTurns = 0;
    this.toxicCounter = 0;
    }


    public void takeDamage(int damage){
        if(currentHP - damage < 0){
            setCurrentHP(0);
        }else{
            setCurrentHP(currentHP - damage);
        }
    }

    public void attack(Pokemon target, Move move, int damage){
        System.out.println(
        species.getName() +
        " used " +
        move.getName()
        );

        target.takeDamage(damage);

        move.setPp(move.getPp() - 1);

        System.out.println(
        target.getSpecies().getName() +
        " HP: " +
        target.getCurrentHP()
    );

    }


    public boolean isFainted() {
        return currentHP <= 0;
    }

    public PokemonSpecies getSpecies() {
        return this.species;
    }



}
