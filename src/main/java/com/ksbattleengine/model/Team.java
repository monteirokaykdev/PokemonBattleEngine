package com.ksbattleengine.model;

import java.util.List;


public class Team {
    private List<Pokemon> pokemons;
    private int activePokemonIndex;

    public Team(List<Pokemon> pokemons) {
        if (pokemons == null || pokemons.isEmpty()) {
            throw new IllegalArgumentException("Team must have at least one Pokémon.");
        }
    
        if (pokemons.size() > 6) {
            throw new IllegalArgumentException("Team cannot have more than 6 Pokémon.");
        }

        this.pokemons = pokemons;
        this.activePokemonIndex = 0;
    }

    public void showTeam() {
        int index = 1;

        for (Pokemon pokemon : pokemons) {
            System.out.println(
                index + " - "
                + pokemon.getSpecies().getName()
                + " HP: "
                + pokemon.getCurrentHP()
            );

            index++;
        }
    }

    public Pokemon getActivePokemon() {
        return pokemons.get(activePokemonIndex);
    }

    public boolean hasAvailablePokemon() {
        return pokemons.stream().anyMatch(pokemon -> !pokemon.isFainted());
    }

    public void switchTo(int index) {
        if (index < 0 || index >= pokemons.size()) {
            throw new IllegalArgumentException("Invalid Pokémon index.");
        }

        if (pokemons.get(index).isFainted()) {
            throw new IllegalArgumentException("Cannot switch to a fainted Pokémon.");
        }

        this.activePokemonIndex = index;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }
    
    public boolean contains(Pokemon pokemon) {
    return pokemons.contains(pokemon);
}
}
