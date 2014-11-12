package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Universe;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.readers.ShipReader;
import edu.gatech.gem5.game.ui.Incrementor;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * FXML Controller class.
 *
 * @author James Jong Han Park
 * @author Jack
 */
public class CharacterCreateController extends AbstractController {

    /**
     * Text field for character name.
     */
    @FXML
    TextField txtName;
    /**
     * Incrementor for player's pilot skill points (See Incrementor class for
     * more details). It can also have a decrementing feature.
     */
    @FXML
    Incrementor incPilot;
    /**
     * Incrementor for player's fighter skill points.
     */
    @FXML
    Incrementor incFighter;
    /**
     * Incrementor for player's trader skill points.
     */
    @FXML
    Incrementor incTrader;
    /**
     * Incrementor for player's engineer skill points.
     */
    @FXML
    Incrementor incEngineer;
    /**
     * Incrementor for player's investor skill points.
     */
    @FXML
    Incrementor incInvestor;

    /**
     * Label to show currently available skill points for the interacting
     * player.
     */
    @FXML
    Label lblSkillPoints;
    /**
     * Error label used for showing error messages to the interacting player.
     */
    @FXML
    Label lblError;
    /**
     * Property for player's skill points.
     */
    private IntegerProperty skillPoints;
    /**
     * FXML file that is directly associated with this class.
     */
    public static final String CREATE_VIEW_FILE = "/fxml/create.fxml";

    /**
     * Default ship for the player.
     */
    public static final String DEFAULT_SHIP = "vagabond";
    /**
     * Maximum character name length.
     */
    public static final int MAX_NAME_LENGTH = 8;
    /**
     * Starting skill points.
     */
    public static final int SKILL_POINTS = 20;

    /**
     * Initialize the character creation controller.
     */
    public CharacterCreateController() {
        super(CREATE_VIEW_FILE);

        // initialize available skill points
        skillPoints = new SimpleIntegerProperty(SKILL_POINTS);

        // set maximum value constraints
        incPilot.maxProperty().bind(incPilot.valueProperty().add(skillPoints));
        incFighter.maxProperty().bind(incFighter.valueProperty().add(skillPoints));
        incTrader.maxProperty().bind(incTrader.valueProperty().add(skillPoints));
        incEngineer.maxProperty().bind(incEngineer.valueProperty().add(skillPoints));
        incInvestor.maxProperty().bind(incInvestor.valueProperty().add(skillPoints));
        // set minimum value constraints
        incPilot.setMin(1);
        incFighter.setMin(1);
        incTrader.setMin(1);
        incEngineer.setMin(1);
        incInvestor.setMin(1);

        // add listeners to the incrementors
        incPilot.valueProperty().addListener(new SkillPointListener());
        incFighter.valueProperty().addListener(new SkillPointListener());
        incTrader.valueProperty().addListener(new SkillPointListener());
        incEngineer.valueProperty().addListener(new SkillPointListener());
        incInvestor.valueProperty().addListener(new SkillPointListener());

        // bind the skill point label text to the remaining skill points
        lblSkillPoints.textProperty().bind(skillPoints.asString());

        // limit the length of the character name
        txtName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> obs,
                    String oldValue,
                    String newValue) {
                if (newValue.length() > MAX_NAME_LENGTH) {
                    txtName.setText(oldValue);
                }
            }
        });
    }

    /**
     * Move to the confirm screen.
     *
     * @param event The button press.
     */
    @FXML
    public void confirm(ActionEvent event) {
        String name = txtName.getText().trim();

        if (name.isEmpty()) {
            lblError.setText("Please enter a name.");
            return;
        }

        if (skillPoints.getValue() > 0) {
            lblError.setText("Please allocate all your skill points.");
            return;
        }

        // Set static controller properties for all future controllers
        ShipType defaultShipType = new ShipReader().get(DEFAULT_SHIP);
        this.universe = new Universe();
        this.player = new Character(
                name,
                incPilot.getValue(),
                incFighter.getValue(),
                incTrader.getValue(),
                incEngineer.getValue(),
                incInvestor.getValue(),
                new Ship(defaultShipType)
        );
        this.system = universe.getSolarSystemNear(
                universe,
                universe.getWidth() / 2,
                universe.getHeight() / 2
        );
        this.planet = system.getPlanetAt(0);

        // go to next controller
        transitionTo(new CharacterStatusController());
    }

    /**
     * Go back to the title screen.
     *
     * @param event a button press
     * @throws Exception propagates any JavaFX Exception
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        transitionTo(new TitleController());
    }

    /**
     * A listener for limiting the skill points you can spend.
     *
     * @author Creston Bunch
     */
    private class SkillPointListener implements ChangeListener<Number> {

        @Override
        public void changed(ObservableValue<? extends Number> obs,
                Number oldValue,
                Number newValue) {
            skillPoints.setValue(
                    skillPoints.getValue()
                    - newValue.intValue()
                    + oldValue.intValue()
            );
        }
    }
}
