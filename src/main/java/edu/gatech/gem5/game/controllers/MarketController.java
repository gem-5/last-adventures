package edu.gatech.gem5.game.controllers;

import java.util.Map;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.Planet;
import edu.gatech.gem5.game.Data;
import edu.gatech.gem5.game.SaveFile;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.Ship;
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
public class MarketController extends Controller {

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

    public static final String MARKET_VIEW_FILE = "/fxml/market.fxml";

    /**
     * Construct the planet controller.
     */
    public MarketController() {
        // load the view or throw an exception
        super(MARKET_VIEW_FILE);

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
        Transaction t = new Transaction(player, planet);

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
        Transaction t = new Transaction(player, planet);

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
     * @throws Exception propagates javaFX exceptions
     */
    @FXML
    public void goBack(ActionEvent event) throws Exception {
        // LastAdventures.swap(new PlanetController());
        transitionTo(new PlanetController());
    }

    private void fillLabels() {
        this.lblCash.setText(
            Integer.toString(player.getMoney())
        );
        Ship s = player.getShip();
        this.lblSlots.setText(
            Integer.toString(s.getOpenBays())
        );
        errorLabel.setText("");
    }

    private void buildBuyGoodsList() {
        // this is the tab for goods that the planet sells
        ObservableList<BuyBar> lstGoods = FXCollections.observableArrayList();
        Map<String, GoodType> goods = Data.GOODS.get();
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
        Map<String, Integer> playerGoods = player.getShip().getCargoList();
        Map<String, Integer> prices = planet.getDemand(playerGoods.keySet());
        for (Map.Entry<String, Integer> s : playerGoods.entrySet()) {
            if (s.getValue() == 0) {
                continue; // don't bother with these
            }
            GoodType g = Data.GOODS.get(s.getKey());
            BuyBar b = new BuyBar();
            b.setKey(s.getKey());
            b.setQuantity(s.getValue());
            b.setPrice(prices.get(s.getKey()));
            b.setText(g.getName());
            listGoods.add(b);
        }
        sellGoods.setItems(listGoods);
    }

}
