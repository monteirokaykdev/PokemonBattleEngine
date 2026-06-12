package com.ksbattleengine.model;

import lombok.Getter;

@Getter
public class IndividualValues {

    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;

    public IndividualValues(
        int hp,
        int attack,
        int defense,
        int specialAttack,
        int specialDefense,
        int speed
    ) {
        this.hp = validate(hp);
        this.attack = validate(attack);
        this.defense = validate(defense);
        this.specialAttack = validate(specialAttack);
        this.specialDefense = validate(specialDefense);
        this.speed = validate(speed);
    }

    private int validate(int value) {
        if (value < 0 || value > 31) {
            throw new IllegalArgumentException("IV must be between 0 and 31");
        }

        return value;
    }
}