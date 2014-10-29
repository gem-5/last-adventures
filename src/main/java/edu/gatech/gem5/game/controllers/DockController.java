package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Data;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Shield;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.ui.UpgradeBar;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 *
 * @author James Park
 */
public class DockController extends Controller {

    @FXML
    Button universe;
    @FXML
    private Label errorLabel;
    @FXML
    private Label lblCash;
    @FXML
    private Label shipInfo;
    @FXML
    private ListView<UpgradeBar> buyUpgradeBarList;
    private final Planet planet;

    public static final String SHIPYARD_VIEW_FILE = "/fxml/dock.fxml";

    public DockController() {
        super(SHIPYARD_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();
        refreshLabels();
    }

    /**
     * Go back to the planet screen.
     *
     * @param event A button press attempting to change scenes
     * @throws Exception propagates javaFX exceptions
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        // LastAdventures.swap(new PlanetController());
        transitionTo(new PlanetController());
    }

    private void fillLabels() {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        this.lblCash.setText(
                ((Integer) save.getCharacter().getMoney()).toString()
        );

        // Pull the ship's information
        Ship s = save.getCharacter().getShip();
        this.shipInfo.setText(s.toString());

        // Reset the error label. When this method is called, we assert that there will be no errors..
        errorLabel.setText("");
    }

    // TODO Prevent players from purchasing, when slots are full.
    private void buildBuyUpgradesList() {

        ObservableList<UpgradeBar> upgradeItemsList = FXCollections.observableArrayList();

        // Pull all weapons and shields list
        Map<String, WeaponType> weapons = Data.WEAPONS.get();
        Map<String, ShieldType> shields = Data.SHIELDS.get();

        // Add weapons from a particular planet to the purchase list.
        for (String s : planet.getWeapons()) {
            WeaponType weapon = weapons.get(s);
            UpgradeBar upgradeWeaponBar = new UpgradeBar();
            upgradeWeaponBar.setKey(s);
            upgradeWeaponBar.setText(weapon.getName());
            upgradeWeaponBar.setPrice(weapon.getPrice());
            upgradeWeaponBar.setOnAction(new BuyWeaponHandler(weapon));
            upgradeItemsList.add(upgradeWeaponBar);
        }

        // Add shields from a particular planet to the purchase list.
        for (String s : planet.getShields()) {
            ShieldType shield = shields.get(s);
            UpgradeBar shieldUpgradeBar = new UpgradeBar();
            shieldUpgradeBar.setKey(s);
            shieldUpgradeBar.setText(shield.getName());
            shieldUpgradeBar.setPrice(shield.getPrice());
            shieldUpgradeBar.setOnAction(new BuyShieldHandler(shield));
            upgradeItemsList.add(shieldUpgradeBar);
        }

        // Update upgradeItemsList
        buyUpgradeBarList.setItems(upgradeItemsList);
    }

    /**
     * Helper method to refresh all of the required label. Updates purchases
     * list & player's data.
     */
    private void refreshLabels() {
        buildBuyUpgradesList();
        fillLabels();
    }

    // TODO (James) I was told to do this way, because of the way other classes are set up. There are definitely better ways to do this.
    /**
     * Handles an event for when a player initiates a weapon purchase.
     */
    private class BuyWeaponHandler implements EventHandler<ActionEvent> {

        private final WeaponType weaponType;

        public BuyWeaponHandler(WeaponType weaponType) {
            this.weaponType = weaponType;
        }

        /**
         * Processes transaction, and appropriate weapon allocation.
         *
         * @param t An ActionEvent
         */
        @Override
        public void handle(ActionEvent t) {

            Character player = LastAdventures.getCurrentSaveFile().getCharacter();
            Ship playerShip = player.getShip();

            // Check for player money count (edge case)
            if (player.getMoney() < weaponType.getPrice()) {
                errorLabel.setText("You don't have enough money.");
                return;
            }

            // Process weapon purchase if space, otherwise display an error
            if (!playerShip.addUpgrade(weaponType)) {
                errorLabel.setText("No more weapon slots remaining for the ship.");
                return;
            }
            player.setMoney(player.getMoney() - weaponType.getPrice());

            // Refresh list & money count
            refreshLabels();
        }
    }

    /**
     * Handles an event for when a player initiates a shield purchase.
     */
    private class BuyShieldHandler implements EventHandler<ActionEvent> {

        private final ShieldType shieldType;

        public BuyShieldHandler(ShieldType shieldType) {
            this.shieldType = shieldType;
        }

        /**
         * Processes transaction, and appropriate shield allocation.
         *
         * @param t An ActionEvent
         */
        @Override
        public void handle(ActionEvent t) {

            Character player = LastAdventures.getCurrentSaveFile().getCharacter();
            Ship playerShip = player.getShip();

            // Check for player money count (edge case)
            if (player.getMoney() < shieldType.getPrice()) {
                errorLabel.setText("You don't have enough money.");
                return;
            }

            // Process shield purchase if space, otherwise display an error
            if (!playerShip.addUpgrade(shieldType)) {
                errorLabel.setText("No more shield slots remaining for the ship.");
                return;
            }

            player.setMoney(player.getMoney() - shieldType.getPrice());

            // Refresh list & money count
            refreshLabels();
        }
    }
}
