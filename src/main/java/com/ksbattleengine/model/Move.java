package com.ksbattleengine.model;

import com.ksbattleengine.enums.PokemonType;
import com.ksbattleengine.enums.StatType;
import com.ksbattleengine.enums.Status;

import java.util.List;

import com.ksbattleengine.enums.MoveCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Move {

    private String name;

    private PokemonType type;

    private int power;

    private int accuracy;

    private MoveCategory category;

    private Status statusEffect;

    private int priority;

    private int statusChance;

    private int pp;

    private List<MoveStatChange> statChanges;

    private boolean isTargetSelf;


}
