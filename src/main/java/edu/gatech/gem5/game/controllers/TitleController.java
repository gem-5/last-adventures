package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.animation.FadeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Creston Bunch
 * @author James Jong Han Park
 */
public class TitleController extends Controller {

    @FXML
    private GridPane gridPane;

    public static final String TITLE_VIEW_FILE = "/fxml/title.fxml" ;

    /**
     * Construct the title controller.
     */
    public TitleController() {
        // load the view or throw an exception
        super(TITLE_VIEW_FILE);
    }

    /**
     * Move to the new game scene.
     *
     * @param even a button press
     */
    @FXML
    private void startNewGame(ActionEvent event) throws Exception {
        // LastAdventures.swap(new CharacterCreateController());
        transitionTo(new CharacterCreateController());
    }

    /**
     * Move to the continue game scene.
     *
     * @param even a button press
     */
    @FXML
    private void continueGame(ActionEvent event) throws Exception {
        // LastAdventures.swap(this); // go nowhere
        transitionTo(this);
    }

    /**
     * Move to the load game scene.
     *
     * @param even a button press
     */
    @FXML
    private void loadGame(ActionEvent event) throws Exception {
       // LastAdventures.swap(new LoadGameController());
        transitionTo(new LoadGameController());
    }
}
