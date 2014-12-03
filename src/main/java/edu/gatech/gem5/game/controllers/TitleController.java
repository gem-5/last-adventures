package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.animation.FadeHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

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
     * Holds buttons.
     */
    @FXML
    private GridPane gridPane;

    /**
     * The entire screen.
     */
    @FXML
    private BorderPane defaultScene;

    /**
     * Construct the title controller.
     */
    public TitleController() {
        // load the view or throw an exception
        super(TITLE_VIEW_FILE);
        // Set opacity 0 initially, so that animation will run smoothly.
        gridPane.setOpacity(0);

        // Adds fade-in animation when mouse enters the screen.
        defaultScene.setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                new FadeHandler(gridPane);

            }
        });

        // Adds fade-out animation when mouse leaves the screen.
        defaultScene.setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {
                new FadeHandler(gridPane, 0, .5, 1, 0);
            }
        });

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
