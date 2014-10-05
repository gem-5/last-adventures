package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.Planet;
import java.util.Random;

public class Encounter {

    //TODO: Find a way to properly get these to cast.
    private static final Object[] ships =
        LastAdventures.data.get(ShipType.KEY).values().toArray();

    private static final Object[] weapons =
        LastAdventures.data.get(WeaponType.KEY).values().toArray();

    private static final Object[] shields =
        LastAdventures.data.get(ShieldType.KEY).values().toArray();

    private static final Object[] gadgets =
        LastAdventures.data.get(GadgetType.KEY).values().toArray();

    private static final Object[] goods =
        LastAdventures.data.get(GoodType.KEY).values().toArray();

    private static final Random r = new Random();

    /**
     * Randomly generates a pirate, police, or trader encounter
     * @param p The planet the player is currently travelling to.
     *
     */
    public void getEncounter(Planet p) {
        int seed = Math.max(LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth(), 1);
        getEncounter(seed, p);
    }

    private void getEncounter(int seed, Planet p) {
        Human spawn = null;
        int encounter = r.nextInt(3);
        switch (encounter) {
        case 0:
            spawn = traderEncounter(seed);
            break;
        case 1:
            spawn = policeEncounter(seed);
            break;
        case 2:
            spawn = pirateEncounter(seed);
            break;
        }
        System.out.printf("Encounter with: %s%n", spawn.toString());

    }

    private Pirate pirateEncounter(int seed) {
        int shipIndex = Math.max(r.nextInt(seed) / 7500, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.max(r.nextInt(seed) / 10000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add(new Weapon((WeaponType) weapons[i]));
        }

        int shieldsNum = Math.max(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add(new Shield((ShieldType) shields[i]));
        }

        int gadgetsNum = Math.max(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add(new Gadget((GadgetType) gadgets[i]));
        }

        return Pirate.createPirate(seed, ship);

    }

    private Trader traderEncounter(int seed) {
        int shipIndex = Math.max(r.nextInt(seed) / 10000, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.max(r.nextInt(seed) / 20000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add(new Weapon((WeaponType) weapons[i]));
        }

        int shieldsNum = Math.max(r.nextInt(seed) / 15000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add(new Shield((ShieldType) shields[i]));
        }

        int gadgetsNum = Math.max(r.nextInt(seed) / 10000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add(new Gadget((GadgetType) gadgets[i]));
        }

        return Trader.createTrader(seed, ship);

    }

    private Police policeEncounter(int seed) {
        int shipIndex = Math.max(r.nextInt(seed) / 5000, ships.length - 1);
        ShipType shipT = (ShipType) ships[shipIndex];
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.max(r.nextInt(seed) / 15000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add(new Weapon((WeaponType) weapons[i]));
        }

        int shieldsNum = Math.max(r.nextInt(seed) / 10000, shipT.getShieldSlots() - 1);
        for (int i = 0; i < shieldsNum; i++) {
            ship.getShieldList().add(new Shield((ShieldType) shields[i]));
        }

        int gadgetsNum = Math.max(r.nextInt(seed) / 20000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgetList().add(new Gadget((GadgetType) gadgets[i]));
        }

        return Police.createPolice(seed, ship);

    }

    public static void main(String[] args) {
        Encounter e = new Encounter();
        for (int i = 1; i < 20; i++) {
            e.getEncounter(1000 * i, null);
        }


    }
}