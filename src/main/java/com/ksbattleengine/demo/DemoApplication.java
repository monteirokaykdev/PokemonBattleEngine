package com.ksbattleengine.demo;


import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ksbattleengine.enums.MoveCategory;
import com.ksbattleengine.enums.PokemonType;
import com.ksbattleengine.model.Ability;
import com.ksbattleengine.model.Move;
import com.ksbattleengine.model.Pokemon;
import com.ksbattleengine.model.PokemonSpecies;
import com.ksbattleengine.model.Stats;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        battleTest();
    }

    static PokemonSpecies createCharmander() {
        Ability blaze = new Ability("Blaze", "Boosts fire type attacks");

        Move ember = new Move(
            "Ember",
            PokemonType.FIRE,
            20,
            100,
            MoveCategory.SPECIAL
        );

        Stats stats = new Stats(39, 52, 43, 60, 50, 65);

        return new PokemonSpecies(
            List.of(blaze),
            stats,
            List.of(ember),
            "Charmander",
            4,
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
            MoveCategory.SPECIAL
        );

        Stats stats = new Stats(44, 48, 65, 50, 64, 43);

        return new PokemonSpecies(
            List.of(torrent),
            stats,
            List.of(waterGun),
            "Squirtle",
            7,
            PokemonType.WATER
        );
    }

    static void battleTest() {
        Scanner scanner = new Scanner(System.in);

        Pokemon charmander1 = new Pokemon(createCharmander(), 5);
        Pokemon squirtle1 = new Pokemon(createSquirtle(), 5);

        while (!charmander1.isFainted() && !squirtle1.isFainted()) {
            System.out.println("Choose Charmander's move:");
            charmander1.getSpecies().displayMoves();

            int choice = scanner.nextInt();

            Move selectedMove = charmander1.getSpecies().getMove(choice - 1);
            charmander1.attack(squirtle1, selectedMove);

            if (squirtle1.isFainted()) {
                System.out.println("Squirtle fainted!");
                break;
            }

            System.out.println("Choose Squirtle's move:");
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

        scanner.close();
    }
}
