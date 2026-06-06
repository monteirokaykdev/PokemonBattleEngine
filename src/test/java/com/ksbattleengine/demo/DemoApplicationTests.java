package com.ksbattleengine.demo;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.ksbattleengine.enums.MoveCategory;
import com.ksbattleengine.enums.PokemonType;
import com.ksbattleengine.model.Ability;
import com.ksbattleengine.model.Move;
import com.ksbattleengine.model.Pokemon;
import com.ksbattleengine.model.PokemonSpecies;
import com.ksbattleengine.model.Stats;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void shouldCreatePokemon() {
			Ability blaze = new Ability(
		"Blaze",
		"Boosts fire type attacks"
		);

		Move ember = new Move(
			"Ember",
			PokemonType.FIRE,
			20,
			100,
			MoveCategory.SPECIAL);

		Stats stats = new Stats(
		39, // HP
		52, // Attack
		43, // Defense
		60, // Sp. Attack
		50, // Sp. Defense
		65  // Speed
		);

		PokemonSpecies charmander = new PokemonSpecies(
			List.of(blaze),
		 	stats, 
			List.of(ember),
			"Charmander",
			1,
			PokemonType.FIRE);


		assertEquals("Charmander", charmander.getName());
		assertEquals(PokemonType.FIRE, charmander.getPrimaryType());
		assertEquals("Blaze",charmander.getAbilities().get(0).getName());
		assertEquals("Ember",charmander.getMoves().get(0).getName());

		System.out.println(charmander.getName());
		System.out.println(charmander.getPrimaryType());
		System.out.println(charmander.getAbilities().get(0).getName());
		System.out.println(charmander.getMoves().get(0).getName());

		Ability torrent = new Ability(
			"Torrent",
			"Boosts water type attacks"
		);

		Move waterGun = new Move(
			"Water Gun",
			PokemonType.WATER,
			20,
			100,
			MoveCategory.SPECIAL
		);

		Stats squirtleStats = new Stats(
			44, 48, 65, 50, 64, 43
		);

		PokemonSpecies squirtle = new PokemonSpecies(
			List.of(torrent),
			squirtleStats,
			List.of(waterGun),
			"Squirtle",
			7,
			PokemonType.WATER
		);

		Scanner scanner = new Scanner(System.in);

		Pokemon charmander1 = new Pokemon(charmander, 5);

		Pokemon squirtle1 = new Pokemon(squirtle, 5);

		while(!charmander1.isFainted() && !squirtle1.isFainted()){

			System.out.println("Choose your move:");

			charmander1.getSpecies().displayMoves();

			int choice = scanner.nextInt();

			Move selectedMove = charmander1.getSpecies().getMove(choice - 1);

			charmander1.attack(squirtle1, selectedMove);

			if (squirtle1.isFainted()) {
        	System.out.println("Squirtle fainted!");
    		break;
			}

			System.out.println("Choose your move:");

			squirtle1.getSpecies().displayMoves();

			choice = scanner.nextInt();

			selectedMove = squirtle1.getSpecies().getMove(choice - 1);

			squirtle1.attack(charmander1, selectedMove);

			if (charmander1.isFainted()) {
        	System.out.println("Charmander fainted!");
    		break;
			}

		}

		if (charmander1.isFainted()) {
    	System.out.println("Squirtle wins!");
		} else {
    	System.out.println("Charmander wins!");
		}


	}

}
