package edu.gatech.gem5.game.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Creston Bunch
 * @author James Jong Han Park
 */
public class TitleController extends AbstractController {
    /**
     * The path that the constructor will take.
     */
    public static final String TITLE_VIEW_FILE = "/fxml/title.fxml";

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
     * @param event A button press
     * @throws Exception 
     */
    @FXML
    private void startNewGame(ActionEvent event) throws Exception {
        transitionTo(new CharacterCreateController());
    }

    /**
     * Move to the continue game scene.
     * 
     * @param event A button press
     * @throws Exception 
     */
    @FXML
    private void continueGame(ActionEvent event) throws Exception {
        transitionTo(this);
    }

    /**
     * Move to the load game scene.
     *
     * @param event A button press
     * @throws Exception 
     */
    @FXML
    private void loadGame(ActionEvent event) throws Exception {
        transitionTo(new LoadGameController());
    }
}
