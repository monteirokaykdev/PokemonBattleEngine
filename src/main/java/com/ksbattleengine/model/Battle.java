package com.ksbattleengine.model;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import com.ksbattleengine.enums.MoveCategory;

public class Battle {

    private Pokemon pokemon1;
    private Pokemon pokemon2;
    private Scanner scanner;
    private int turn;

    public Battle(Pokemon pokemon1, Pokemon pokemon2) {
        this.pokemon1 = pokemon1;
        this.pokemon2 = pokemon2;
        this.scanner = new Scanner(System.in);
        this.turn = 1;
    }

    public void start() {
        while (!pokemon1.isFainted() && !pokemon2.isFainted()) {   

            startTurn();
            Pokemon first = getSpeedPrio(pokemon1, pokemon2);

            Pokemon second = first == pokemon1 ? pokemon2 : pokemon1; 

            executeAction(first, second);

            if (second.isFainted()) {
                System.out.println(second.getSpecies().getName() + " fainted!");
                break;
            }

            executeAction(second, first);


            if (first.isFainted()) {
                System.out.println(first.getSpecies().getName() + " fainted!");
                break;
            }

            StatusManager.applyTurnEndStatusEffects(pokemon1);
            StatusManager.applyTurnEndStatusEffects(pokemon2);

            if (first.isFainted()) {
                System.out.println(first.getSpecies().getName() + " fainted!");
                break;
            }

            if (second.isFainted()) {
                System.out.println(second.getSpecies().getName() + " fainted!");
                break;
            }


            turn++;
        }

        showWinner();
    }

    private void executeAction(Pokemon attacker, Pokemon target) {
        while(true){
            System.out.println("Choose " + attacker.getSpecies().getName() + "'s move:");

            attacker.getSpecies().displayMoves();

            int choice = scanner.nextInt();

        if (choice < 1 ||
            choice > attacker.getSpecies()
                             .getMoves()
                             .size()) {

            System.out.println("Invalid Move!");
            continue;
        }

            Move selectedMove = attacker.getSpecies().getMove(choice - 1);

            if(!StatusManager.canAct(attacker)){
                return;
            }

            
            if(BattleRNG.accuracyCheck(selectedMove)){

                if (selectedMove.getCategory() == MoveCategory.STATUS) {
                StatusManager.applyStatusEffect(target, selectedMove);
                System.out.println("It's a status move!");
                return;
                }

                boolean critical = BattleRNG.criticalCheck();

                boolean stab = DamageCalculator.isStab(attacker, selectedMove);

                int damage = DamageCalculator.calculateDamage(attacker, target, selectedMove, critical);

                attacker.attack(target, selectedMove, damage);

                StatusManager.applyStatusEffect(target, selectedMove);

                double typeEffectiveness = TypeChart.getTypeEffectiveness(
                    selectedMove.getType(),
                    target.getSpecies()
                );

                if(critical){
                    System.out.println("A critical hit!");
                }

                if(stab){
                    System.out.println("STAB!");
                }
                
                if(typeEffectiveness > 1.0 && typeEffectiveness < 3.0){
                    System.out.println("It's super effective!");
                }else if(typeEffectiveness < 1.0){
                    System.out.println("It's not very effective...");
                }else if(typeEffectiveness == 0.0){
                    System.out.println("It has no effect...");
                }else if(typeEffectiveness >= 3.0){
                    System.out.println("It's extremely effective!");
                }else if(typeEffectiveness < 0.5){
                    System.out.println("It's barely effective...");
                }



                System.out.println("Type Effectiveness: " + typeEffectiveness);
                System.out.println("Status: " + target.getStatus());

                return;

            }else{
                if(selectedMove.getCategory() == MoveCategory.STATUS){
                    System.out.println("But it failed!");
                }
                System.out.println(
                attacker.getSpecies().getName()
                + "'s "
                + selectedMove.getName()
                + " missed!"
                );
            }
                return;
            }

        }

    private Pokemon getSpeedPrio(Pokemon p1,Pokemon p2){
        int speed1 = p1.getSpecies().getBaseStats().getSpeed();
        int speed2 = p2.getSpecies().getBaseStats().getSpeed();

        if(speed1 > speed2){
            return p1;
        }

        if(speed2 > speed1){
            return p2;
        }

        //tie breaker
        return ThreadLocalRandom.current().nextBoolean() ? p1:p2;
    }

    private void showWinner() {
        if (pokemon1.isFainted()) {
            System.out.println(pokemon2.getSpecies().getName() + " wins!");
        } else {
            System.out.println(pokemon1.getSpecies().getName() + " wins!");
        }
    }

    private void startTurn() {
    System.out.println("\n====================");
    System.out.println("TURN " + turn);
    System.out.println("====================");
    }
}
