package com.ksbattleengine.model;

import java.util.List;

import com.ksbattleengine.enums.PokemonType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PokemonSpecies {

    private int pokedexNumber;

    private String name;
    private PokemonType primaryType;
    private Stats baseStats;

    private List<Ability> abilities;
    private List<Move> moves;

    public PokemonSpecies(List<Ability> abilities, Stats baseStats, List<Move> moves, String name, int pokedexNumber, PokemonType primaryType) {
        this.abilities = abilities;
        this.baseStats = baseStats;
        this.moves = moves;
        this.name = name;
        this.pokedexNumber = pokedexNumber;
        this.primaryType = primaryType;
    }

    public Move getMove(int moveIndex) {
    if (moveIndex < 0 || moveIndex >= moves.size()) {
        throw new IllegalArgumentException("Invalid move index");
    }

    return moves.get(moveIndex);
    }

    public void displayMoves() {
        for (int i = 0; i < moves.size(); i++) {
            System.out.println(
            (i + 1) + " - " +
            moves.get(i).getName()
        );
    }
}
    
    
    
}
