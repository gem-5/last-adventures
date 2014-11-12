package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import edu.gatech.gem5.game.Trader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * FXML Controller Class.
 *
 * Shows trader encounter during flight
 *
 * @author Sam Blumenthal
 * @author James Jong Han Park
 */
public class TraderEncounterController extends EncounterController {

    /**
     * A button to initiate a trade.
     */
    @FXML
    Button trade;

    /**
     * Construct the trader encounter controller.
     * 
     * @param encounter The type of encounter
     */
    // public static final String TRADER_VIEW_FILE = "/fxml/traderencounter.fxml";
    public TraderEncounterController(Encounterable encounter) {
        super(encounter);
    }

    /**
     * Transitions the screen to one where the player can trade with a trader.
     * 
     * @param event
     * @throws Exception
     */
    @FXML
    public void tradeWithTrader(ActionEvent event) throws Exception {
        // LastAdventures.swap(new TraderController((Trader) encounter));
        transitionTo(new TraderController((Trader) encounter));
    }

    // public void attackEnemy(ActionEvent event) throws Exception {
    //     // not yet implemented
    // }
}
