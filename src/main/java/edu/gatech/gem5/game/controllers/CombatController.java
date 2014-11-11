package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.AbstractNPC;
import edu.gatech.gem5.game.AbstractHuman;
import edu.gatech.gem5.game.Character;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import java.util.Random;

/**
 * FXML Controller Class.
 *
 * Shows trader encounter during flight
 *
 * @author Sam Blumenthal
 * @author James Jong Han Park
 */
public class CombatController extends Controller {

    /**
     * Button for attacking an enemy.
     */
    @FXML
    Button attack;
    /**
     * Button for fleeing an enemy.
     */
    @FXML
    Button flee;
    /**
     * Continue button after an enemy has been defeated.
     */
    @FXML
    Button cont;

    /**
     * Current interacting player.
     */
    Character player;
    /**
     * An encountered enemy by the interacting player.
     */
    AbstractNPC enemy;
    /**
     * A message that is shown after winning a battle.
     */
    @FXML
    Text dialog;
    /**
     * An FXML file that is directly associated with this class.
     */
    public static final String COMBAT_VIEW_FILE = "/fxml/combat.fxml";

    /**
     * Construct the combat controller.
     *
     * @param p the Character involved in combat
     * @param e the enemy NPC involved in combat
     */
    public CombatController(Character p, AbstractNPC e) {
        super(COMBAT_VIEW_FILE);
        this.player = p;
        this.enemy = e;

        cont.setVisible(false);

        enactCombat();
    }
    
    public void attackEnemy(ActionEvent event) throws Exception {
        enactCombat();
    }

    public void flee(ActionEvent event) throws Exception {
        //TODO functionality

        /*if (fleeAttempt(player, enemy)) {
         // player flees battle
         } else {
         // player fails, enemy attacks during the attempt
         }*/
    }

    private void enactCombat() {
        String result = String.format("%s:%n", player.getName());
        result += this.player.attackShip(enemy.getShip());
        if (enemy.getShip().isDestroyed()) {
            result += String.format("The enemy, %s, has been defeated!%n"
                    + "You loot %d off their corpse.",
                    enemy.getName(), enemy.getMoney());
            player.setMoney(player.getMoney() + enemy.getMoney());
            attack.setVisible(false);
            flee.setVisible(false);
            cont.setVisible(true);
        } else {
            result += String.format("%n%s:%n", enemy.getName());
            result += this.enemy.attackShip(player.getShip());
            if (player.getShip().isDestroyed()) {
                result += String.format("With that last blow, the enemy, %s, has destroyed your Ship.%n"
                        + "You escape to the planet, but without a Ship or any cargo.",
                        enemy.getName());
                attack.setVisible(false);
                flee.setVisible(false);
                cont.setVisible(true);
            }
        }
        dialog.setText(result);
    }

    private boolean fleeAttempt(AbstractHuman h1, AbstractHuman h2) {
        Random r = new Random();
        int variation = r.nextInt(h1.getPilot() / 10) - h1.getPilot() / 20;
        variation += r.nextInt(h2.getPilot() / 10) - h2.getPilot() / 20;

        return h1.getPilot() + variation > h2.getPilot();
    }

    public void continueToPlanet(ActionEvent event) throws Exception {
        enemy.getManager().nextEncounter();
    }
}
