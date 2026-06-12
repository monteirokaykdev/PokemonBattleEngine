	package com.ksbattleengine.demo;


	import java.util.List;

	import com.ksbattleengine.enums.MoveCategory;
	import com.ksbattleengine.enums.Nature;
	import com.ksbattleengine.enums.PokemonType;
	import com.ksbattleengine.enums.StatType;
	import com.ksbattleengine.enums.Status;
	import com.ksbattleengine.model.Ability;
	import com.ksbattleengine.model.Battle;
	import com.ksbattleengine.model.IndividualValues;
	import com.ksbattleengine.model.Move;
import com.ksbattleengine.model.MoveStatChange;
import com.ksbattleengine.model.Pokemon;
	import com.ksbattleengine.model.PokemonSpecies;
	import com.ksbattleengine.model.Stats;
import com.ksbattleengine.model.Team;
import com.ksbattleengine.model.EffortValues;



	//@SpringBootApplication
	public class DemoApplication {

		public static void main(String[] args) {
			//SpringApplication.run(DemoApplication.class, args);


			IndividualValues ivs = new IndividualValues(31, 31, 31, 31, 31, 31);
			EffortValues evs = new EffortValues(0, 252, 0, 0, 0, 252);

			Team team1 = new Team(
				List.of(
					new Pokemon(createCharmander(), 10, Nature.ADAMANT, ivs, evs),
					new Pokemon(createCharmander(), 10, Nature.JOLLY, ivs, evs),
					new Pokemon(createCharmander(), 10, Nature.BRAVE, ivs, evs),
					new Pokemon(createCharmander(), 10, Nature.HARDY, ivs, evs),
					new Pokemon(createCharmander(), 10, Nature.LONELY, ivs, evs),
					new Pokemon(createCharmander(), 10, Nature.NAUGHTY, ivs, evs)
				)
			);

			Team team2 = new Team(
				List.of(
					new Pokemon(createSquirtle(), 10, Nature.MODEST, ivs, evs),
					new Pokemon(createSquirtle(), 10, Nature.TIMID, ivs, evs),
					new Pokemon(createSquirtle(), 10, Nature.CALM, ivs, evs),
					new Pokemon(createSquirtle(), 10, Nature.BOLD, ivs, evs),
					new Pokemon(createSquirtle(), 10, Nature.MILD, ivs, evs),
					new Pokemon(createSquirtle(), 10, Nature.QUIET, ivs, evs)
				)
			);

			Battle battle = new Battle(team1, team2);

			battle.start();


		}

		static PokemonSpecies createCharmander() {
			Ability blaze = new Ability("Blaze", "Boosts fire type attacks");

			Move ember = new Move(
				"Ember",
				PokemonType.FIRE,
				20,
				100,
				MoveCategory.PHYSICAL,
				Status.NONE,
				0,
				100,
				25,
				List.of(),
				false
			);

			Move Will_O_Wisp = new Move(
				"Will O Wisp",
				PokemonType.FIRE,
				0,
				100,
				MoveCategory.STATUS,
				Status.BURN,
				0,
				100,
				25,
				List.of(),
				false
			);

			Move swordsDance = new Move(
				"Swords Dance",
				PokemonType.NORMAL,
				0,
				-1,
				MoveCategory.STATUS,
				Status.NONE,
				0,
				0,
				20,
				List.of(
					new MoveStatChange(
						StatType.ATTACK,
						2
					)
				),
				true
			);

			Stats stats = new Stats(2000, 52, 43, 60, 50, 100);

			return new PokemonSpecies(
				List.of(blaze),
				stats,
				List.of(ember, Will_O_Wisp,swordsDance),
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
				Status.NONE,
				0,
				0,
				25,
				List.of(),
				false
				
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
