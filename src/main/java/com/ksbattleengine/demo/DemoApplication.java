	package com.ksbattleengine.demo;


	import java.util.List;

	import com.ksbattleengine.enums.MoveCategory;
	import com.ksbattleengine.enums.PokemonType;
	import com.ksbattleengine.enums.Status;
	import com.ksbattleengine.model.Ability;
	import com.ksbattleengine.model.Battle;
	import com.ksbattleengine.model.Move;
	import com.ksbattleengine.model.Pokemon;
	import com.ksbattleengine.model.PokemonSpecies;
	import com.ksbattleengine.model.Stats;


	//@SpringBootApplication
	public class DemoApplication {

		public static void main(String[] args) {
			//SpringApplication.run(DemoApplication.class, args);
			
			Pokemon charmander1 = new Pokemon(createCharmander(), 5);
			Pokemon squirtle1 = new Pokemon(createSquirtle(), 5);

			Battle battle = new Battle(charmander1, squirtle1);

			battle.start();


		}

		static PokemonSpecies createCharmander() {
			Ability blaze = new Ability("Blaze", "Boosts fire type attacks");

			Move ember = new Move(
				"Ember",
				PokemonType.FIRE,
				20,
				1,
				MoveCategory.SPECIAL,
				Status.NONE
			);

			Move Will_O_Wisp = new Move(
				"Will O Wisp",
				PokemonType.FIRE,
				0,
				100,
				MoveCategory.STATUS,
				Status.BURN
			);

			Stats stats = new Stats(200, 52, 43, 60, 50, 100);

			return new PokemonSpecies(
				List.of(blaze),
				stats,
				List.of(ember, Will_O_Wisp),
				"Charmander",
				4,
				PokemonType.FIRE,
				PokemonType.FIRE
			);
		}

		static PokemonSpecies createSquirtle() {
			Ability torrent = new Ability("Torrent", "Boosts water type attacks");

			Move waterGun = new Move(
				"Water Gun",
				PokemonType.WATER,
				20,
				100,
				MoveCategory.SPECIAL,
				Status.NONE
			);


			Stats stats = new Stats(44, 48, 65, 50, 64, 100);

			return new PokemonSpecies(
				List.of(torrent),
				stats,
				List.of(waterGun),
				"Squirtle",
				7,
				PokemonType.WATER
			);
		}
			
	}
