package com.ksbattleengine.model;

import com.ksbattleengine.enums.StatType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MoveStatChange {

    private StatType stat;
    private int amount;
}