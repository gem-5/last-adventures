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

    // public static final String TRADER_VIEW_FILE = "/fxml/traderencounter.fxml";
    public TraderEncounterController(Encounterable encounter) {
        super(encounter);
    }

    /**
     * Construct the trader encounter controller.
     *
     * @param the Trader that the Character has encountered.
     */
    //public TraderEncounterController(Trader t) {
    //super(t, TRADER_VIEW_FILE);
    //}
    @FXML
    public void tradeWithTrader(ActionEvent event) throws Exception {
        // LastAdventures.swap(new TraderController((Trader) encounter));
        transitionTo(new TraderController((Trader) encounter));
    }

    // public void attackEnemy(ActionEvent event) throws Exception {
    //     // not yet implemented
    // }
}
