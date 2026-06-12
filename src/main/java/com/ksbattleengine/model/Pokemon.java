package com.ksbattleengine.model;

import com.ksbattleengine.enums.Nature;
import com.ksbattleengine.enums.Status;
import com.ksbattleengine.enums.StatType;
import com.ksbattleengine.model.MoveStatChange;

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
    private Nature nature;
    private IndividualValues ivs;
    private EffortValues evs;
    private Stats finalStats;
    private StatStage statStages;

    public Pokemon(PokemonSpecies species,int level,Nature nature,IndividualValues ivs,EffortValues evs) {
        this.species = species;
        this.level = level;
        this.nature = nature;
        this.ivs = ivs;
        this.evs = evs;
        this.finalStats = calculateFinalStats();
        this.currentHP = finalStats.getHp();
        this.statStages = new StatStage();
        this.status = Status.NONE;
        this.confused = false;
        this.sleepTurns = 0;
        this.confusionTurns = 0;
        this.toxicCounter = 0;
    }

    public Pokemon(PokemonSpecies species, int level) {
    this(
        species,
        level,
        Nature.HARDY,
        new IndividualValues(31, 31, 31, 31, 31, 31),
        new EffortValues(0, 0, 0, 0, 0, 0)
        );
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

    private Stats calculateFinalStats() {
    Stats base = species.getBaseStats();

    return new Stats(
        StatCalculator.calculateHP(
            base.getHp(),
            ivs.getHp(),
            evs.getHp(),
            level
        ),
        StatCalculator.calculateOtherStat(
            base.getAttack(),
            ivs.getAttack(),
            evs.getAttack(),
            level,
            nature,
            StatType.ATTACK
        ),
        StatCalculator.calculateOtherStat(
            base.getDefense(),
            ivs.getDefense(),
            evs.getDefense(),
            level,
            nature,
            StatType.DEFENSE
        ),
        StatCalculator.calculateOtherStat(
            base.getSpecialAttack(),
            ivs.getSpecialAttack(),
            evs.getSpecialAttack(),
            level,
            nature,
            StatType.SPECIAL_ATTACK
        ),
        StatCalculator.calculateOtherStat(
            base.getSpecialDefense(),
            ivs.getSpecialDefense(),
            evs.getSpecialDefense(),
            level,
            nature,
            StatType.SPECIAL_DEFENSE
        ),
        StatCalculator.calculateOtherStat(
            base.getSpeed(),
            ivs.getSpeed(),
            evs.getSpeed(),
            level,
            nature,
            StatType.SPEED
        )
    );
}


    public boolean isFainted() {
        return currentHP <= 0;
    }

    public PokemonSpecies getSpecies() {
        return this.species;
    }



}
