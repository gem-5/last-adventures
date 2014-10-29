package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShieldType;

public class Shield {
    private final ShieldType type;
    private double remainingIntegrity;

    public Shield(ShieldType type) {
        this.type = type;
        this.remainingIntegrity = type.getIntegrity();
    }

    public ShieldType getType() {
        return type;
    }

    public double getHealth() {
        return this.remainingIntegrity;
    }

    public void decrementHealth(double damage) {
        this.remainingIntegrity -= damage;
        this.remainingIntegrity = Math.max(0, remainingIntegrity);
    }

    public double maxHealth() {
        return this.type.getIntegrity();
    }

}
