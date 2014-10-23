package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.NPC;
import edu.gatech.gem5.game.Human;
import edu.gatech.gem5.game.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.util.Random;

/**
 * FXML Controller Class
 *
 * Shows trader encounter during flight
 *
 * @author Sam Blumenthal
 */
public class CombatController extends Controller {


    @FXML
    Button attack;
    Button flee;

    Character player;
    NPC enemy;


    @FXML
    Text dialog;

    public static final String COMBAT_VIEW_FILE = "/fxml/combat.fxml";

    /**
     * Contruct the combat controller
     * @param p the Character involved in combat
     * @param e the enemy NPC involved in combat
     */
    public CombatController(Character p, NPC e) {
        super(COMBAT_VIEW_FILE);
        this.player = p;
        this.enemy = e;

        enactCombat();
    }


    public void attackEnemy(ActionEvent event) throws Exception {
        enactCombat();
    }

    public void flee(ActionEvent event) throws Exception {
        if (fleeAttempt(player, enemy)) {
            // player flees battle
        } else {
            // player fails, enemy attacks during the attempt
        }
    }

    private void enactCombat() {
        String result = "";
        result += this.player.attackShip(enemy.getShip());
        if (enemy.getShip().isDestroyed()) {
            result += String.format("The enemy, %s, has been defeated!%n"
                                    + "You loot %d off their corpse.",
                                    enemy.getName(), enemy.getMoney());
            player.setMoney(player.getMoney() + enemy.getMoney());
        } else {
            result += this.enemy.attackShip(player.getShip());
            if (player.getShip().isDestroyed()) {
                result += String.format("With that last blow, the enemy, %s, has destroyed your Ship.%n"
                                        + "You escape to the planet, but without a Ship or any cargo.",
                                        enemy.getName());
            }
        }
        dialog.setText(result);
    }

    private boolean fleeAttempt(Human h1, Human h2) {
        Random r = new Random();
        int variation = r.nextInt(h1.getPilot() / 10) - h1.getPilot() / 20;
        variation += r.nextInt(h2.getPilot() / 10) - h2.getPilot() / 20;

        return h1.getPilot() + variation > h2.getPilot();
    }
}
