package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Encounterable;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Trader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller Class
 *
 * Shows trader encounter during flight
 *
 * @author Sam Blumenthal
 */
public class TraderEncounterController extends EncounterController {


    @FXML
    Button trade;


    // public static final String TRADER_VIEW_FILE = "/fxml/traderencounter.fxml";

    public TraderEncounterController(Encounterable encounter) {
        super(encounter);
    }

    /**
     * Contruct the trader encounter controller
     * @param t the Trader that the Character has encountered.
     */
    //public TraderEncounterController(Trader t) {
        //super(t, TRADER_VIEW_FILE);
    //}

    @FXML
    public void tradeWithTrader(ActionEvent event) throws Exception {
        LastAdventures.swap(new TraderController((Trader) encounter));
    }

    // public void attackEnemy(ActionEvent event) throws Exception {
    //     // not yet implemented
    // }
}
