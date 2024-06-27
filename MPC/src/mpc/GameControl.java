package mpc;

import java.util.Scanner;

public class GameControl {

    // Player and Monster objects
    private Player player;
    private Monster currentMonster;

    // Scanner for user input
    Scanner scnr = new Scanner(System.in);

    // Method to start the game and initialize the player
    public void startGame() {
        System.out.print("\nPlease name your character: ");
        String name = scnr.nextLine();
        player = new Player(name);
    }

    // Method to create a random monster
    public Monster createMonster() {
        int num = (int) (Math.random() * 4) + 1;
        switch (num) {
            case 1: return new Goblin();
            case 2: return new Knight();
            case 3: return new Werewolf();
            case 4: return new StatueOfGod();
            default: return new Goblin();
        }
    }

    // Method to initiate the next level and start a battle
    public void nextLevel() {
        currentMonster = createMonster();
        System.out.println(currentMonster.getName() + " has appeared");
        System.out.println("\nThere is no escape... YOU MUST FIGHT");
        player.displayStats();
        currentMonster.displayStats();
        displayCombat();
    }

    // Method to handle the combat display and interactions
    public void displayCombat() {
        player.reduceHunger(currentMonster);

        // Loop while both the monster and player are alive and the player is not hungry
        while (currentMonster.isAlive() && player.isAlive() && !player.isHungry()) {
            boolean validInput = false;
            System.out.println("What would you like to do?");
            System.out.println("\n[1] Fight\n[2] Use Item");
            int choice = scnr.nextInt();
            scnr.nextLine();

            // Handle user input for combat options
            while (!validInput) {
                if (choice == 1) {
                    fight(); 
                    validInput = true;
                } else if (choice == 2) {
                    player.selectItem(currentMonster);
                    if (!player.block() && !currentMonster.isPoison()) {
                        currentMonster.attackPlayer(player);
                    }
                    validInput = true;
                } else {
                    System.out.println("Invalid input");
                }
            }
        }

        // Handle player being too hungry to fight
        if (player.isHungry()) {
            System.out.println("\nYou are too hungry to fight.");
            // Add method to eat
        }

        // Handle player losing the fight
        if (!player.isAlive()) {
            System.out.println("\nYou have lost the fight");
            player.train();
        }
    }

    // Method to handle the fight sequence between the player and monster
    public void fight() {
        player.attackMonster(currentMonster);

        // Special case for StatueOfGod monster
        if (currentMonster instanceof StatueOfGod) {
            if (((StatueOfGod) currentMonster).isDefeat()) {
                System.out.println("\nYOU HAVE WON THE GAME");
                System.exit(0);
            }
        }

        // Handle monster defeat
        if (!currentMonster.isAlive()) {
            System.out.println("\nCongratulations, you have won the fight against " + currentMonster.getName());
            currentMonster.generateXP(player);
            player.handleVictory(currentMonster);
            return;
        }

        // Handle player blocking and monster attacking
        if (!player.block() && currentMonster.isAlive()) {
            currentMonster.attackPlayer(player);
        }
    }

    // Method to display the game menu
    public void displayMenu() {
        System.out.println("\n\t🔹 Press '1' to Enter the Battle Arena and Begin Fighting!");
        System.out.println("\n\t🔹 Press '2' to Visit the Mystical Item Shop!");
        System.out.println("\n\t🔹 Press '3' to Open Your Adventurer's Inventory!");

        int num = scnr.nextInt();
        scnr.nextLine();

        // Handle user input for menu options
        if (num == 1) {
            nextLevel();
        } else if (num == 2) {
            player.itemShop();
        } else if (num == 3) {
            player.displayInventory();
        } else {
            System.out.println("Invalid input");
            return;
        }
    }
}
