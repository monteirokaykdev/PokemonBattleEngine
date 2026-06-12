package com.ksbattleengine.model;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.ksbattleengine.enums.MoveCategory;
import com.ksbattleengine.enums.StatType;

public class Battle {

    private Team team1;
    private Team team2;
    private Scanner scanner;
    private int turn;

    public Battle(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.scanner = new Scanner(System.in);
        this.turn = 1;
    }

    public void start() {
        chooseStartingPokemon(team1, "P1");
        chooseStartingPokemon(team2, "P2");

        while (team1.hasAvailablePokemon() && team2.hasAvailablePokemon()) {
            Pokemon pokemon1 = team1.getActivePokemon();
            Pokemon pokemon2 = team2.getActivePokemon();

            startTurn();

            Pokemon first = getSpeedPrio(pokemon1, pokemon2);
            Pokemon second = first == pokemon1 ? pokemon2 : pokemon1;

            Team firstTeam = getTeamOf(first);
            Team secondTeam = getTeamOf(second);

            chooseTurnAction(first, firstTeam, second);

            if (second.isFainted()) {
                System.out.println(second.getSpecies().getName() + " fainted!");
                handleFaintedPokemon(second);
                turn++;
                continue;
            }

            if (!team1.hasAvailablePokemon() || !team2.hasAvailablePokemon()) {
                break;
            }

            Pokemon updatedTargetForSecond =
                firstTeam.getActivePokemon();

            chooseTurnAction(second, secondTeam, updatedTargetForSecond);

            if (updatedTargetForSecond.isFainted()) {
                System.out.println(updatedTargetForSecond.getSpecies().getName() + " fainted!");
                handleFaintedPokemon(updatedTargetForSecond);
                turn++;
                continue;
            }

            if (!team1.hasAvailablePokemon() || !team2.hasAvailablePokemon()) {
                break;
            }

            Pokemon active1 = team1.getActivePokemon();
            Pokemon active2 = team2.getActivePokemon();

            StatusManager.applyTurnEndStatusEffects(active1);
            StatusManager.applyTurnEndStatusEffects(active2);

            if (active1.isFainted()) {
                System.out.println(active1.getSpecies().getName() + " fainted!");
                handleFaintedPokemon(active1);
                turn++;
                continue;
            }

            if (active2.isFainted()) {
                System.out.println(active2.getSpecies().getName() + " fainted!");
                handleFaintedPokemon(active2);
                turn++;
                continue;
            }

            turn++;
        }

        showWinner();
    }

    private void executeAction(Pokemon attacker, Pokemon target) {
        while (true) {
            System.out.println("Choose " + attacker.getSpecies().getName() + "'s move:");

            attacker.getSpecies().displayMoves();

            int choice = scanner.nextInt();

            if (choice < 1 || choice > attacker.getSpecies().getMoves().size()) {
                System.out.println("Invalid Move!");
                continue;
            }

            Move selectedMove = attacker.getSpecies().getMove(choice - 1);

            if (!StatusManager.canAct(attacker)) {
                return;
            }

            if (BattleRNG.accuracyCheck(selectedMove)) {

                if (selectedMove.getCategory() == MoveCategory.STATUS) {
                    StatusManager.applyStatusEffect(target, selectedMove);

                    StatManager.applyStatChange(
                        attacker,
                        target,
                        selectedMove
                    );

                    System.out.println("It's a status move!");
                    return;
                }

                boolean critical = BattleRNG.criticalCheck();

                boolean stab = DamageCalculator.isStab(attacker, selectedMove);

                int damage = DamageCalculator.calculateDamage(
                    attacker,
                    target,
                    selectedMove,
                    critical
                );

                attacker.attack(target, selectedMove, damage);

                StatManager.applyStatChange(attacker, target, selectedMove);

                StatusManager.applyStatusEffect(target, selectedMove);

                double typeEffectiveness = TypeChart.getTypeEffectiveness(
                    selectedMove.getType(),
                    target.getSpecies()
                );

                if (critical) {
                    System.out.println("A critical hit!");
                }

                if (stab) {
                    System.out.println("STAB!");
                }

                if (typeEffectiveness > 1.0 && typeEffectiveness < 3.0) {
                    System.out.println("It's super effective!");
                } else if (typeEffectiveness < 1.0 && typeEffectiveness > 0.0) {
                    System.out.println("It's not very effective...");
                } else if (typeEffectiveness == 0.0) {
                    System.out.println("It has no effect...");
                } else if (typeEffectiveness >= 3.0) {
                    System.out.println("It's extremely effective!");
                }

                System.out.println("Type Effectiveness: " + typeEffectiveness);
                System.out.println("Status: " + target.getStatus());

                return;

            } else {
                if (selectedMove.getCategory() == MoveCategory.STATUS) {
                    System.out.println("But it failed!");
                }

                System.out.println(
                    attacker.getSpecies().getName()
                    + "'s "
                    + selectedMove.getName()
                    + " missed!"
                );

                return;
            }
        }
    }

    private boolean chooseTurnAction(
        Pokemon attacker,
        Team attackerTeam,
        Pokemon target
    ) {
        while (true) {
            System.out.println("Choose action for " + attacker.getSpecies().getName() + ":");
            System.out.println("1 - Fight");
            System.out.println("2 - Switch");

            int choice = scanner.nextInt();

            if (choice == 1) {
                executeAction(attacker, target);
                return true;
            }

            if (choice == 2) {
                String playerName = attackerTeam == team1 ? "P1" : "P2";

                chooseReplacementPokemon(attackerTeam, playerName);

                return false;
            }

            System.out.println("Invalid action!");
        }
    }

    private Pokemon getSpeedPrio(Pokemon p1, Pokemon p2) {
        int speed1 = (int) (
            p1.getFinalStats().getSpeed()
            * p1.getStatStages().getMultiplier(StatType.SPEED)
        );

        int speed2 = (int) (
            p2.getFinalStats().getSpeed()
            * p2.getStatStages().getMultiplier(StatType.SPEED)
        );

        if (speed1 > speed2) {
            return p1;
        }

        if (speed2 > speed1) {
            return p2;
        }

        return ThreadLocalRandom.current().nextBoolean() ? p1 : p2;
    }

    private void chooseStartingPokemon(Team team, String playerName) {
        while (true) {
            System.out.println(playerName + ", choose your starting Pokémon:");

            team.showTeam();

            int choice = scanner.nextInt();
            int index = choice - 1;

            try {
                team.switchTo(index);

                System.out.println(
                    playerName
                    + " sent out "
                    + team.getActivePokemon().getSpecies().getName()
                    + "!"
                );

                return;

            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
            }
        }
    }

    private void chooseReplacementPokemon(Team team, String playerName) {
        if (!team.hasAvailablePokemon()) {
            return;
        }

        while (true) {
            System.out.println(playerName + ", choose a replacement:");

            team.showTeam();

            int choice = scanner.nextInt();
            int index = choice - 1;

            try {
                team.switchTo(index);

                System.out.println(
                    playerName
                    + " sent out "
                    + team.getActivePokemon().getSpecies().getName()
                    + "!"
                );

                return;

            } catch (IllegalArgumentException error) {
                System.out.println(error.getMessage());
            }
        }
    }

    private void handleFaintedPokemon(Pokemon faintedPokemon) {
        Team team = getTeamOf(faintedPokemon);
        String playerName = team == team1 ? "P1" : "P2";

        if (team.hasAvailablePokemon()) {
            chooseReplacementPokemon(team, playerName);
        }
    }

    private Team getTeamOf(Pokemon pokemon) {
        if (team1.contains(pokemon)) {
            return team1;
        }

        return team2;
    }

    private void showWinner() {
        if (!team1.hasAvailablePokemon()) {
            System.out.println("Team 2 wins!");
        } else if (!team2.hasAvailablePokemon()) {
            System.out.println("Team 1 wins!");
        }
    }

    private void startTurn() {
        System.out.println("\n====================");
        System.out.println("TURN " + turn);
        System.out.println("====================");
    }
}