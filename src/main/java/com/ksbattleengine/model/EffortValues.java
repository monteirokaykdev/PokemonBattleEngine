package com.ksbattleengine.model;

import lombok.Getter;

@Getter
public class EffortValues {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public EffortValues(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
    ) {
        validateTotal(hp, attack, defense, specialAttack, specialDefense, speed);

        this.hp = validateSingle(hp);
        this.attack = validateSingle(attack);
        this.defense = validateSingle(defense);
        this.specialAttack = validateSingle(specialAttack);
        this.specialDefense = validateSingle(specialDefense);
        this.speed = validateSingle(speed);
    }
    private int validateSingle(int value) {
        if (value < 0 || value > 252) {
            throw new IllegalArgumentException("Each EV must be between 0 and 252");
        }

        return value;
    }

     private void validateTotal(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
    ) {
        int total =
            hp + attack + defense + specialAttack + specialDefense + speed;

        if (total > 510) {
            throw new IllegalArgumentException("Total EVs cannot exceed 510");
        }
    }
}