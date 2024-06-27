package mpc;

import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Scanner;

public class Player {

    // Player attributes
    String name;
    private int health;
    private int attackPower;
    private int hunger;
    private boolean isCritical;
    private int level = 1;
    private int xp;
    private int immunityCount = 0;
    private int boostCount = 0;
    private ArrayList<Item> items;
    private ArrayList<Integer> itemQuantities;
    private int monsterLevel = 0;

    // Scanner for user input
    Scanner scnr = new Scanner(System.in);

    // Constructor to initialize player attributes
    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.attackPower = 15;
        this.hunger = 100;
        this.isCritical = false;
        this.monsterLevel = 1;
        this.items = new ArrayList<>();
        this.xp = 0;
        this.itemQuantities = new ArrayList<>();
        // Adding initial items to the player's inventory
        addItem("Golden Apple", 200, "A rare fruit that grants extraordinary health and hunger (100 HP, 100 Hunger)", 1);
        addItem("Poison of Death", 100, "A deadly concoction with lethal effects (25 DMG)", 1);
        addItem("Elixir of Vitality", 400, "A powerful elixir that grants immunity to all damage", 1);
        addItem("Titan's Fist", 400, "A legendary artifact that bestows immense strength", 1);
        addItem("Phoenix Feather", 600, "A mystical feather that disintegrates the enemy", 1);
    }

    // Method to handle monster defeat and player leveling up
    public void monsterDefeat(Monster monster) {
        if (monster.getLevel() == monsterLevel) {
            levelUp();
        } else if (monster.getLevel() >= level) {
            int monsterLevel = monster.getLevel();
            int levelGained = monsterLevel - level + 1;
            for (int i = 0; i < levelGained; i++) {
                levelUp();
            }
        }
    }

    // Method to make the player immune to damage for 3 attacks
    public void isImmune() {
        immunityCount = 3;
        System.out.println("A mystical shield surrounds you! You are immune to the next 3 attacks!");
    }

    // Method to activate strength boost for 3 turns
    public void activateStrength() {
        boostCount = 3;
        System.out.println("A surge of power courses through you! Your attacks will deal extra damage for the next 3 turns!");
    }

    // Method to add an item to the player's inventory
    public void addItem(String name, int price, String description, int count) {
        Item newItem = new Item(name, price, description);
        items.add(newItem);
        itemQuantities.add(count);
    }

    // Method to select and use an item from the inventory
    public void selectItem(Monster currentMonster) {
        System.out.println("\n🌟 Choose Your Item, Mighty Hero! 🌟");
        System.out.println("\n1. Golden Apple - A rare fruit that grants extraordinary health and hunger (100 HP, 100 Hunger)");
        System.out.println("2. Poison of Death - A deadly concoction with lethal effects (25 DMG)");
        System.out.println("3. Elixir of Vitality - A powerful elixir that grants immunity to all damage");
        System.out.println("4. Titan's Fist - A legendary artifact that bestows immense strength");
        System.out.println("5. Phoenix Feather - A mystical feather that disintegrates the enemy");
        System.out.println("6. Exit Inventory");
        System.out.println("\nEnter the number of the item you wish to use:");

        int choice = scnr.nextInt();
        scnr.nextLine();

        if (choice == 6) {
            System.out.println("Exiting Inventory...");
            return;
        }

        if (choice < 1 || choice > itemQuantities.size()) {
            System.out.println("Invalid choice. Please select a valid item.");
            return;
        }

        int index = choice - 1;
        int current = itemQuantities.get(index);

        if (current > 0) {
            itemQuantities.set(index, current - 1);
            applyEffect(choice, currentMonster);
        } else {
            System.out.println("You do not have this item");
        }
    }

    // Method to apply the effect of the selected item
    public void applyEffect(int choice, Monster monster) {
        switch (choice) {
            case 1:
                System.out.println("\nGolden Apple has been used");
                this.health += 100;
                this.hunger += 100;
                System.out.println("\nYour HP has been increased to " + health);
                System.out.println("\nYour hunger has been increased to " + hunger);
                break;

            case 2:
                System.out.println("You have poisoned the enemy for 5 seconds");
                poisonMonster(monster);
                break;

            case 3:
                System.out.println("Elixir of Vitality has been used");
                isImmune();
                break;

            case 4:
                System.out.println("Titan's Fist has been used");
                activateStrength();
                break;

            case 5:
                System.out.println("You use the Phoenix Feather! A burst of fiery energy engulfs your enemy, reducing them to ashes instantly!");
                instantKill(monster);
                if (!monster.isAlive()) {
                    System.out.println("\nCongratulations, you have won the fight against " + monster.getName());
                    monster.generateXP(this);
                    handleVictory(monster);
                }
                break;
        }
    }

    // Method to handle actions after a victory
    public void handleVictory(Monster monster) {
        System.out.println("\n🌟 Victory Achieved! 🌟");
        System.out.println("\nYou have earned precious Battle XP: " + monster.getXP() + " points.");
        System.out.println("Total XP: " + getXP());
        System.out.println("\nRemember, you cannot fight on an empty stomach.");

        increaseHunger();
        System.out.println("\nYou feast on a hearty meal, replenishing your hunger to " + getHunger() + "!");
        System.out.println("\nNOW YOU CAN PREPARE FOR THE NEXT BATTLE! ⚔️");

        monsterDefeat(monster);
    }

    // Method to apply poison effect on a monster
    public void poisonMonster(Monster monster) {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int remainingTime = 5;

            @Override
            public void run() {
                if (remainingTime > 0) {
                    monster.setHealth(monster.getHealth() - 5);
                    remainingTime--;

                    if (monster.getHealth() <= 0) {
                        timer.cancel();
                        System.out.println(monster.getName() + " has died...\n");
                        return;
                    } else {
                        System.out.println(monster.getName() + " health is now " + monster.getHealth());
                    }
                } else {
                    System.out.println(monster.getName() + " is no longer poisoned");
                    timer.cancel();
                }
            }
        }, 0, 1000);
        monster.isPoison();
    }

    // Method to instantly kill a monster
    public void instantKill(Monster monster) {
        monster.setHealth(0);
        System.out.println("\n" + monster.getName() + " is dead");
    }

    // Method to display the item shop
    public void itemShop() {
        System.out.println("\tWelcome to the item shop!!");
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            System.out.println("\n[" + (i + 1) + "] " + currentItem.getName() + "\n    Description: " + currentItem.getDescription() + "\n    Price: " + currentItem.getPrice() + " XP");
        }

        System.out.println("\n[6] Leave");

        System.out.println("\nYour XP: " + this.xp);
        System.out.print("\nSelect which item you would like to buy: ");
        int choice = scnr.nextInt();
        scnr.nextLine();

        if (choice <= 6 && choice > 0) {
            int index = choice - 1;

            switch (choice) {
                case 1:
                    purchaseItem("Golden Apple", 100, index);
                    break;

                case 2:
                    purchaseItem("Poison of Death", 300, index);
                    break;

                case 3:
                    purchaseItem("Elixir of Vitality", 500, index);
                    break;

                case 4:
                    purchaseItem("Titan's Fist", 500, index);
                    break;

                case 5:
                    purchaseItem("Phoenix Feather", 100, index);
                    break;

                case 6:
                    return;
            }
        } else {
            System.out.println("Invalid item choice.");
        }
    }

    // Method to purchase an item from the shop
    public void purchaseItem(String name, int price, int index) {
        int count = itemQuantities.get(index);
        if (xp >= price) {
            xp -= price;
            System.out.println("Congratulations, you have purchased a " + name + " !");
            System.out.println("Your XP is now " + xp);
            itemQuantities.set(index, count + 1);
            System.out.println("You have " + itemQuantities.get(index) + " " + name + " in your inventory");
        } else {
            System.out.println("\nNot enough XP to purchase the item");
        }
    }

    // Method to display the player's inventory
    public void displayInventory() {
        System.out.println("\tAdventurer's Inventory!");
        for (int i = 0; i < items.size(); i++) {
            Item currentItem = items.get(i);
            int count = itemQuantities.get(i);
            System.out.println("\n[" + (i + 1) + "] " + currentItem.getName() + "\nPrice: " + currentItem.getPrice() + "\nAmount: " + count);
        }
    }

    // Method to check for a critical hit chance
    public boolean checkForCritical() {
        if (Math.random() < 0.1) {
            isCritical = true;
        }
        return isCritical;
    }

    // Method to level up the player
    public void levelUp() {
        this.level++;
        int newHealth = 100 * level;
        this.health = newHealth;
        this.attackPower += 15;
        this.hunger += 50;
    }

    // Method to apply a critical hit
    public void applyCriticalHit() {
        if (checkForCritical()) {
            this.attackPower *= 2;
            System.out.println("\nCRITICAL HIT");
        }
    }

    // Method to reset the player's attack power after a critical hit or boost
    public void resetAttackPower(int baseAttack) {
        if (isCritical || boostCount > 0) {
            attackPower = baseAttack;
        }
    }

    // Method for the player to take damage
    public void takeDamage(int damage, Monster monster) {
        if (immunityCount > 0) {
            immunityCount--;
            System.out.println("Your shield absorbs the blow! You can block " + immunityCount + " more attacks.");
        } else {
            health -= damage;
            System.out.printf("\t\nYou took %d damage. Your health is now %d\n", damage, health < 0 ? 0 : health);
        }
        if (!monster.isAlive()) {
            immunityCount = 0;
        }
    }

    // Method to attack a monster
    public void attackMonster(Monster monster) {
        int baseAttack = attackPower;
        applyCriticalHit();
        if (boostCount > 0) {
            boostCount--;
            this.attackPower *= 2;
            System.out.println("You unleash a powerful strike!");
        }
        monster.takeDamage(attackPower);
        System.out.println("\nYou attacked the " + monster.getName() + " for " + attackPower + " damage");
        System.out.printf("%s health is now %d\n", monster.getName(), monster.getHealth() < 0 ? 0 : monster.getHealth());
        resetAttackPower(baseAttack);
        if (!monster.isAlive()) {
            boostCount = 0;
        }
    }

    // Method to reduce the player's hunger based on the monster's hunger depletion
    public void reduceHunger(Monster monster) {
        this.hunger -= monster.getHungerDepletion();
        if (hunger < 20) {
            this.hunger = 0;
        }
        System.out.println("\t\nYour hunger is now " + hunger);
    }

    // Method to check if the player is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Method to check if the player is hungry
    public boolean isHungry() {
        return hunger < 0;
    }

    // Getter for player name
    public String getName() {
        return name;
    }

    // Getter for attack power
    public int getAttackPower() {
        return attackPower;
    }

    // Getter for hunger
    public int getHunger() {
        return hunger;
    }

    // Method to increase the player's hunger
    public void increaseHunger() {
        hunger += 50;
    }

    // Getter for XP
    public int getXP() {
        return xp;
    }

    // Method for the player to train after being defeated
    public void train() {
        int random = (int) (Math.random() * 2) + 1;
        System.out.println("\n\n⚔️ You have been defeated... ");
        System.out.println("The weight of defeat hangs heavy in the air. Your courage faltered, and now you must face the consequences.");
        System.out.println("Your hard-earned battle experience has been lost to the winds of fate.");
        System.out.println("\nBut this is not the end. Every defeat is a lesson in disguise. Rise from the ashes, train harder, and become stronger.");
        System.out.println("⚔️ The journey of a true warrior is forged through relentless training and perseverance.");
        System.out.println("\nYou need to train harder to regain your strength and honor. Never give up!");

        resetStats();

        switch (random) {
            case 1:
                health += 15;
                System.out.println("\t\nYour health has increased to " + health);
                break;
            case 2:
                attackPower += 5;
                System.out.println("\t\nYour attack power has increased to " + attackPower);
                break;
        }
    }

    // Method to display the player's stats
    public void displayStats() {
        System.out.printf("\t\nName: %s\nHealth: %d\nAttack Power: %d\nHunger: %d\nLevel: %d\n", name, health, attackPower, hunger, level);
    }

    // Method to reset the player's stats
    public void resetStats() {
        this.health = 100;
        this.attackPower = 15;
        this.hunger = 100;
        this.level = 1;
    }

    // Method to block an attack with a 20% chance
    public boolean block() {
        if (Math.random() < 0.2) {
            System.out.println("\t\nYou have blocked the attack!!");
            return true;
        }
        return false;
    }

    // Setter for XP
    public void setXP(int xp) {
        this.xp = xp;
    }

    // Method to inflict poison on the player
    public void poison() {
        int baseHealth = health;

        if (immunityCount > 0) {
            System.out.println("\nYour immunity has saved you from poison\n");
            return;
        } else if (Math.random() < 0.2 && health > 0) {
            System.out.println("\n\tYou have been inflicted with poison\n");

            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                int remainingTime = 8;

                @Override
                public void run() {
                    if (remainingTime > 0) {
                        health -= 5;
                        remainingTime--;

                        if (health <= 0) {
                            timer.cancel();
                            System.out.println("\nYou have died...");
                            train();

                        } else {
                            System.out.println("\nYour health is now " + health);
                        }
                    } else {
                        timer.cancel();
                    }
                }
            }, 0, 1000);

            System.out.println("Press [1] to cure the poison with a golden apple: ");
            System.out.println("Press [2] to continue fighting: \n");

            int choice = scnr.nextInt();

            // Golden apple being used
            if (choice == 1) {
                if (itemQuantities.get(0) > 0) {
                    System.out.println("You've been cured of poison!");
                    System.out.println("Your health is back up to " + baseHealth);
                    this.health = baseHealth;
                    itemQuantities.set(0, itemQuantities.get(0) - 1);
                    timer.cancel();
                } else {
                    System.out.println("You have no golden apple");
                    return;
                }
            } else if (choice == 2) {
                System.out.println("You have chosen to keep on fighting\n\nYour damage will be reduced by 10");
                this.attackPower -= 10;
                if (health < 0) {
                    timer.cancel();
                }
            }
            timer.cancel();
        }
    }
}
