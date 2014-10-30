package edu.gatech.gem5.game.controllers;

import java.util.Map;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.Data;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Trader;
import edu.gatech.gem5.game.ui.BuyBar;
import edu.gatech.gem5.game.data.GoodType;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author James
 */
public class TraderController extends Controller {

    @FXML
    AnchorPane root;
    @FXML
    Button universe;

    @FXML
    private Label lblCash;

    @FXML
    private Label lblSlots;

    @FXML
    private Label errorLabel;

    // @FXML
    // private Button refuelButton;
    @FXML
    private ListView<BuyBar> buyGoods;
    @FXML
    private ListView<BuyBar> sellGoods;

    private final Trader trader;
    public static final String MARKET_VIEW_FILE = "/fxml/trader.fxml";

    /**
     * Construct the trader controller.
     * @param trader
     */
    public TraderController(Trader trader) {
        // load the view or throw an exception
        super(MARKET_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        this.trader = trader;

        fillLabels();
        buildBuyGoodsList();
        buildSellGoodsList();
    }

    /**
     * Commit a purchase.
     *
     * @param event A button event.
     * @throws Exception if something stupid happens
     */
    @FXML
    public void makePurchase(ActionEvent event) throws Exception {
        // enumerate purchases
        Map<String, Integer> purchases = new HashMap<>();
        for (BuyBar bar : buyGoods.getItems()) {
            String productKey = bar.getProduct();
            int quantity = bar.getSliderValue();
            purchases.put(productKey, quantity);
        }
        // construct a transaction between a player and a trader
        Transaction t = new Transaction(
                LastAdventures.getCurrentSaveFile().getCharacter(),
                this.trader
        );
        // attempt the purchase
        if (t.validateBuy(purchases)) {
            t.buy(purchases);
            buildBuyGoodsList(); //necessary for when traders have wealth and stock
            buildSellGoodsList(); //necessary for updateing the ListView of cargo
            fillLabels(); //update the money label
        } else {
            errorLabel.setText(t.getErrorMessage());
        }
    }

    /**
     * Commit a sale.
     *
     * @param event A button event.
     * @throws Exception if something stupid happens
     */
    @FXML
    public void makeSale(ActionEvent event) throws Exception {
        // enumerate sales
        Map<String, Integer> sales = new HashMap<>();
        for (BuyBar bar : sellGoods.getItems()) {
            String productKey = bar.getProduct();
            int quantity = bar.getSliderValue();
            sales.put(productKey, quantity);
        }
        // construct a transaction between a player and a trader
        Transaction t = new Transaction(
                LastAdventures.getCurrentSaveFile().getCharacter(),
                // LastAdventures.getCurrentSaveFile().getPlanet()
                this.trader
        );
        // attempt the sale
        if (t.validateSell(sales)) {
            t.sell(sales);
            // update the labels
            buildBuyGoodsList(); //necessary for when traders have wealth and stock
            buildSellGoodsList(); //necessary for updateing the ListView of cargo
            fillLabels(); //update the money label
        } else {
            errorLabel.setText(t.getErrorMessage());
        }
    }

    // @FXML
    // /**
    //  * Max out the fuel in your ship.
    //  *
    //  * @param event A button press attempting to refuel
    //  * @throws Exception
    //  */
    // public void refuel(ActionEvent event) throws Exception {
    //     Character player = LastAdventures.getCurrentSaveFile().getCharacter();
    //     Ship ship = player.getShip();
    //     player.setMoney(player.getMoney() -  (ship.getType().getRange() -
    //             ship.getFuel()) * ship.getType().getFuelCost() );
    //     ship.setFuel(ship.getType().getRange());
    //     fillLabels();
    // }
    /**
     * Continue to the planet screen.
     *
     * @param event A button press attempting to change scenes
     * @throws Exception propagates javaFX exceptions
     */
    @FXML
    public void continueToPlanet(ActionEvent event) throws Exception {
        //"continue to planet" in sense of get further along in your trip
        trader.getManager().nextEncounter();
    }

    private void fillLabels() {
        SaveFile save = LastAdventures.getCurrentSaveFile();
        this.lblCash.setText(
                ((Integer) save.getCharacter().getMoney()).toString()
        );
        Ship s = save.getCharacter().getShip();
        this.lblSlots.setText(
                ((Integer) s.getOpenBays()).toString()
        );
        errorLabel.setText("");
    }
    /*
     private void buildShipList() {
     // this is the tab for ships that the planet sells
     ObservableList<UpgradeBar> lstShips
     = FXCollections.observableArrayList();
     Map<String, ShipType> ships = LastAdventures.data.get(ShipType.KEY);
     for (String x : planet.getShips()) {
     UpgradeBar b = new UpgradeBar();
     ShipType ship = (ShipType) ships.get(x);
     b.setKey(x);
     b.setPrice(ship.getPrice());
     b.setText(ship.getName());
     lstShips.add(b);
     }
     upShips.setItems(lstShips);
     }

     private void buildWeaponList() {
     // this is the tab for weapons that the planet sells
     ObservableList<UpgradeBar> lstWeapons
     = FXCollections.observableArrayList();
     Map<String, WeaponType> weps = LastAdventures.data.get(WeaponType.KEY);
     for (String x : planet.getWeapons()) {
     UpgradeBar b = new UpgradeBar();
     WeaponType weapon = (WeaponType) weps.get(x);
     b.setKey(x);
     b.setPrice(weapon.getPrice());
     b.setText(weapon.getName());
     lstWeapons.add(b);
     }
     upWeapons.setItems(lstWeapons);
     }

     private void buildShieldList() {
     // this is the tab for shields that the planet sells
     ObservableList<UpgradeBar> lstShields
     = FXCollections.observableArrayList();
     Map<String, ShieldType> shilds = LastAdventures.data.get(ShieldType.KEY);
     for (String x : planet.getShields()) {
     UpgradeBar b = new UpgradeBar();
     ShieldType shield = (ShieldType) shilds.get(x);
     b.setKey(x);
     b.setPrice(shield.getPrice());
     b.setText(shield.getName());
     lstShields.add(b);
     }
     upShields.setItems(lstShields);
     }*/

    private void buildBuyGoodsList() {
        // this is the tab for goods that the trader sells
        ObservableList<BuyBar> lstGoods = FXCollections.observableArrayList();
        Map<String, GoodType> goods = Data.GOODS.get();
        for (Map.Entry<String, Integer> x : trader.getStock().entrySet()) {
            BuyBar b = new BuyBar();
            b.setKey(x.getKey());
            b.setQuantity(x.getValue());
            b.setPrice(trader.getSupply().get(x.getKey()));
            b.setText(
                    ((GoodType) goods.get(x.getKey())).getName()
            );
            lstGoods.add(b);
        }
        buyGoods.setItems(lstGoods);
    }

    private void buildSellGoodsList() {
        ObservableList<BuyBar> listGoods = FXCollections.observableArrayList();
        Map<String, Integer> playerGoods = LastAdventures.getCurrentSaveFile().getCharacter().getShip().getCargoList();
        for (Map.Entry<String, Integer> s : playerGoods.entrySet()) {
            if (s.getValue() == 0) {
                continue; // don't bother with these
            }
            GoodType g = (GoodType) Data.GOODS.get(s.getKey());
            BuyBar b = new BuyBar();
            b.setKey(s.getKey());
            b.setQuantity(s.getValue());
            b.setPrice(trader.getDemand().get(s.getKey()));
            b.setText(g.getName());
            listGoods.add(b);
        }
        sellGoods.setItems(listGoods);
    }

}
