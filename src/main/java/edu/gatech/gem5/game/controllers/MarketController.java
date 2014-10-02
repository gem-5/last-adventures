package edu.gatech.gem5.game.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Map;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.LastAdventures;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Character;
import edu.gatech.gem5.game.Good;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.ui.BuyBar;
import edu.gatech.gem5.game.ui.UpgradeBar;
import edu.gatech.gem5.game.data.DataType;
import edu.gatech.gem5.game.data.GoodType;
import edu.gatech.gem5.game.data.ShipType;
import edu.gatech.gem5.game.data.WeaponType;
import edu.gatech.gem5.game.data.ShieldType;
import edu.gatech.gem5.game.data.GadgetType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author James
 */
public class MarketController extends Controller {
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

    @FXML
    private ListView<BuyBar> buyGoods;
    @FXML
    private ListView<BuyBar> sellGoods;


    private Planet planet;
    public static final String MARKET_VIEW_FILE = "/fxml/market.fxml";

    /**
     * Construct the planet controller.
     */
    public MarketController() {
        // load the view or throw an exception
        super(MARKET_VIEW_FILE);

        SaveFile save = LastAdventures.getCurrentSaveFile();
        planet = save.getPlanet();

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
        // construct a transaction between a player and a planet
        Transaction t = new Transaction(
            LastAdventures.getCurrentSaveFile().getCharacter(),
            LastAdventures.getCurrentSaveFile().getPlanet()
        );
        // attempt the purchase
        if (t.validateBuy(purchases)) {
            t.buy(purchases);
            buildBuyGoodsList(); //necessary for when planets have wealth and stock
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
        // construct a transaction between a player and a planet
        Transaction t = new Transaction(
            LastAdventures.getCurrentSaveFile().getCharacter(),
            LastAdventures.getCurrentSaveFile().getPlanet()
        );
        // attempt the sale
        if (t.validateSell(sales)) {
            t.sell(sales);
            // update the labels
            buildBuyGoodsList(); //necessary for when planets have wealth and stock
            buildSellGoodsList(); //necessary for updateing the ListView of cargo
            fillLabels(); //update the money label
        } else {
            errorLabel.setText(t.getErrorMessage());
        }
    }

    /**
     * Go back to the planet screen.
     *
     * @param event A button press attempting to change scenes
     * @throws Exception
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        LastAdventures.swap(new PlanetController());
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
        // this is the tab for goods that the planet sells
        ObservableList<BuyBar> lstGoods = FXCollections.observableArrayList();
        Map<String, GoodType> goods = LastAdventures.data.get(GoodType.KEY);
        for (Map.Entry<String, Integer> x : planet.getStock().entrySet()) {
            BuyBar b = new BuyBar();
            b.setKey(x.getKey());
            b.setQuantity(x.getValue());
            b.setPrice(planet.getSupply().get(x.getKey()));
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
            if (s.getValue() == 0) continue; // don't bother with these
            GoodType g = (GoodType) LastAdventures.data.get(GoodType.KEY).get(s.getKey());
            BuyBar b = new BuyBar();
            b.setKey(s.getKey());
            b.setQuantity(s.getValue());
            b.setPrice(planet.getDemand().get(s.getKey()));
            b.setText(g.getName());
            // b.setText(
            //           ((GoodType) goods.get(g.getKey())).getName()
            //           );
            listGoods.add(b);
        }
        sellGoods.setItems(listGoods);
    }

    public void changeScenes(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String id = ((Button)(event.getSource())).idProperty().get();
        if (id.equals("universe")) {
            root = FXMLLoader.load(getClass().getResource("/displayUniverse.fxml"));

            stage.setScene(new Scene((Pane) root));
        }
    }
}
