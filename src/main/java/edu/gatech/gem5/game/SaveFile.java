package edu.gatech.gem5.game;

/**
 *
 * @author Jack
 */
public class SaveFile {

    private Character player;
    private Universe universe;
    private Planet currentPlanet;

    public SaveFile() {
        this.player = null;
        this.universe = null;
        this.currentPlanet = null;
    }

    public void addCharacter(Character player) {
        this.player = player;
    }

    public void addUniverse(Universe universe) {
        this.universe = universe;
    }

    public Character getCharacter() {
        return player;
    }

    public Universe getUniverse() {
        return universe;
    }

    public Planet getPlanet() {
        return currentPlanet;
    }

    public void setCurrentPlanet(Planet planet) {
        currentPlanet = planet;
    }

    @Override
    public String toString() {
        return String.format("Player:%n%1s\nPilot: %d%nFighter: %d%nEngineer: "+
                "%d%nTrader: %d%nInvestor: %d", player.getName(),
                player.getPilot(), player.getFighter(), player.getEngineer(),
                player.getTrader(), player.getInvestor()) + "\n" +  universe.toString();


    }
}
