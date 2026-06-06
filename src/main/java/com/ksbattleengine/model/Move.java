package com.ksbattleengine.model;

import com.ksbattleengine.enums.PokemonType;
import com.ksbattleengine.enums.MoveCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Move {

    private String name;

    private PokemonType type;

    private int power;

    private int accuracy;

    private MoveCategory category;
}
