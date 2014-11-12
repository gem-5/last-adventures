package edu.gatech.gem5.game.controllers;

import edu.gatech.gem5.game.Data;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * A controller class for Dock. Features upgrading ship's weapons and shields.
 *
 * @author James Jong Han Park
 */
public class DockController extends AbstractController {

    /**
     * An error message to show if any error occurs.
     */
    @FXML
    private Label errorLabel;
    /**
     * This string didn't pass checkstyle because it was identical when used in
     * the weapon/shield/gadget buyhandlers. This can be fixed by giving
     * weapon/shield/gadget a common interface or superclass.
     */
    String moneyError = "You don't have enough money.";
    
    /**
     * A balance label to show player's current balance.
     */
    @FXML
    private Label lblCash;
    /**
     * A label to show interacting player's ship information.
     */
    @FXML
    private Label shipInfo;
    /**
     * List of UpgradeBars which presents and handles available upgrades to the
     * player.
     */
    @FXML
    private ListView<UpgradeBar> buyUpgradeBarList;

    /**
     * An FXML file that is directly associated with this class.
     */
    public static final String SHIPYARD_VIEW_FILE = "/fxml/dock.fxml";

    /**
     * Constructor for DockController.
     */
    public DockController() {
        super(SHIPYARD_VIEW_FILE);

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
        transitionTo(new PlanetController());
    }

    /**
     * Fills the changeable labels on the screen with their new values once 
     * a purchase has been made.
     */
    private void fillLabels() {
        this.lblCash.setText(((Integer) player.getMoney()).toString());

        // Pull the ship's information
        Ship s = player.getShip();
        this.shipInfo.setText(s.toString());

        // Reset the error label. When this method is called, we assert that there will be no errors..
        errorLabel.setText("");
    }

    // TODO Prevent players from purchasing, when slots are full.
    /**
     * Populates the buyUpgradeBarList member with the upgrades on the planet.
     * The upgrades on a planet are determined by the companies that are on the
     * planet.
     */
    private void buildBuyUpgradesList() {

        ObservableList<UpgradeBar> upgradeItemsList = FXCollections.observableArrayList();

        // Pull all weapons and shields list
        Map<String, WeaponType> weapons = Data.WEAPONS.get();
        Map<String, ShieldType> shields = Data.SHIELDS.get();

        // Add weapons from a particular planet to the purchase list.
        for (String s : planet.getWeapons()) {
            WeaponType weapon = weapons.get(s);
            UpgradeBar upgradeWeaponBar = new UpgradeBar();
            upgradeWeaponBar.setText(weapon.getName());
            upgradeWeaponBar.setPrice(weapon.getPrice());
            upgradeWeaponBar.setOnAction(new BuyWeaponHandler(weapon));
            upgradeItemsList.add(upgradeWeaponBar);
        }

        // Add shields from a particular planet to the purchase list.
        for (String s : planet.getShields()) {
            ShieldType shield = shields.get(s);
            UpgradeBar shieldUpgradeBar = new UpgradeBar();
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

        /**
         * The flyweight for the weapon that is being bought.
         */
        private final WeaponType weaponType;
        
        /**
         * 
         * @param wT the weapon type chosen by the user 
         */
        public BuyWeaponHandler(WeaponType wT) {
            this.weaponType = wT;
        }

        /**
         * Processes transaction, and appropriate weapon allocation.
         *
         * @param t An ActionEvent
         */
        @Override
        public void handle(ActionEvent t) {

            Ship playerShip = player.getShip();

            // Check for player money count (edge case)
            if (player.getMoney() < weaponType.getPrice()) {

                errorLabel.setText(moneyError);
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

        /**
         * The flyweight for the shield that is being bought.
         */
        private final ShieldType shieldType;

        /**
         * 
         * @param sT the shieldType chosen by the user
         */
        public BuyShieldHandler(ShieldType sT) {
            this.shieldType = sT;
        }

        /**
         * Processes transaction, and appropriate shield allocation.
         *
         * @param t An ActionEvent
         */
        @Override
        public void handle(ActionEvent t) {

            Ship playerShip = player.getShip();

            // Check for player money count (edge case)
            if (player.getMoney() < shieldType.getPrice()) {
                errorLabel.setText(moneyError);
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
