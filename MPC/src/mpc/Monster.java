package mpc;

public abstract class Monster {
    
    // Monster attributes
    protected int health;
    protected int attackPower;
    protected int hungerDepletion;
    protected int level;
    protected int monsterXP;

    // Constructor to initialize monster attributes
    public Monster(int health, int attackPower, int hunger, int level) {
        this.health = health;
        this.attackPower = attackPower;
        this.hungerDepletion = hunger;
        this.level = level;
    }

    // Method for monster to attack the player
    public void attackPlayer(Player player) {
        player.takeDamage(attackPower, this);
    }

    // Method for the monster to take damage
    public void takeDamage(int damage) {
        health -= damage;
    }

    // Getter for attack power
    public int getDamage() {
        return attackPower;
    }

    // Getter for hunger depletion
    public int getHungerDepletion() {
        return hungerDepletion;
    }

    // Setter for attack power
    public void setDamage(int damage) {
        this.attackPower = damage;
    }

    // Setter for hunger depletion
    public void setHungerDepletion(int hunger) {
        this.hungerDepletion = hunger;
    }

    // Getter for health
    public int getHealth() {
        return health;
    }

    // Setter for health
    public void setHealth(int health) {
        this.health = health;
    }

    // Method to check if the monster is alive
    public boolean isAlive() {
        return health > 0;
    }

    // Method to check if the monster is dead
    public boolean isDead() {
        return !isAlive();
    }

    // Method to get the monster's name
    public String getName() {
        return getClass().getSimpleName();
    }

    // Method to display the monster's stats
    public void displayStats() {
        System.out.printf("\nMonster: %s\nLevel: %d\nAttack Power: %d\nHunger Depletion: %d\n", getName(), level, attackPower, hungerDepletion);
    }

    // Method to check if the monster is poisoned (default to true)
    public boolean isPoison() {
        return true;
    }

    // Getter for the monster's level
    public int getLevel() {
        return level;
    }

    // Method to generate experience points (XP) for the player after defeating the monster
    public void generateXP(Player player) {
        int xp = 0;
        switch (getLevel()) {
            case 1: xp = (int) (Math.random() * 100); break;
            case 2: xp = (int) (Math.random() * 100) + 100; break;
            case 3: xp = (int) (Math.random() * 100) + 200; break;
            case 4: xp = (int) (Math.random() * 100) + 300; break;
        }
        monsterXP = xp;
        player.setXP(player.getXP() + xp);
    }

    // Getter for the monster's XP
    public int getXP() {
        return monsterXP;
    }
}
