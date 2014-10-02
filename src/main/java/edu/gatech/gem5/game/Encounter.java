package edu.gatech.gem5.game;

import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import edu.gatech.gem5.game.data.GoodType;
import java.util.Random;

public class Encounter {

    private static final String[] ships =
        (String[]) LastAdventures.data.get(ShipType.KEY).keySet().toArray();

    private static final String[] weapons =
        (String[]) LastAdventures.data.get(WeaponType.KEY).keySet().toArray();

    private static final String[] shields =
        (String[]) LastAdventures.data.get(ShieldType.KEY).keySet().toArray();

    private static final String[] gadgets =
        (String[]) LastAdventures.data.get(GadgetType.KEY).keySet().toArray();
    private static final String[] goods =
        (String[]) LastAdventures.data.get(GoodType.KEY).keySet().toArray();

    private static final Random r = new Random();


    public void getEncounter() {
        int encounter = r.nextInt(3);
        int seed = LastAdventures.getCurrentSaveFile().getCharacter().getNetWorth();
        switch (encounter) {
        case 0:
            traderEncounter(seed);
            break;
        case 1:
            policeEncounter(seed);
            break;
        case 2:
            pirateEncounter(seed);
            break;
        }

    }

    private void pirateEncounter(int seed) {
        int shipIndex = Math.max(r.nextInt(seed) / 5000, ships.length - 1);
        ShipType shipT = LastAdventures.data.get(ShipType.KEY).get(ships[shipIndex]);
        Ship ship = new Ship(shipT);

        int weaponsNum = Math.max(r.nextInt(seed) / 10000, shipT.getWeaponSlots() - 1);
        for (int i = 0; i < weaponsNum; i++) {
            ship.getWeaponList().add(weapons[i]);
        }

        int gadgetsNum = Math.max(r.netInt(seed) / 20000, shipT.getGadgetSlots() - 1);
        for (int i = 0; i < gadgetsNum; i++) {
            ship.getGadgestList().add(gadgets[i]);
        }

    }


}
