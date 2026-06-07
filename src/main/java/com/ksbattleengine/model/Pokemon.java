package com.ksbattleengine.model;

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

    public Pokemon(PokemonSpecies species, int level) {
    this.species = species;
    this.level = level;
    this.currentHP = species.getBaseStats().getHp();
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
