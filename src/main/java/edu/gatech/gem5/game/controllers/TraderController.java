package edu.gatech.gem5.game.controllers;

import java.util.Map;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import edu.gatech.gem5.game.Data;
import edu.gatech.gem5.game.Transaction;
import edu.gatech.gem5.game.Ship;
import edu.gatech.gem5.game.Trader;
import edu.gatech.gem5.game.ui.BuyBar;
import edu.gatech.gem5.game.data.GoodType;
import javafx.event.ActionEvent;

/**
 * FXML Controller class.
 *
 * @author James
 */
public class TraderController extends AbstractController {
    /**
     * The label containing the amount of credits the player has.
     */
    @FXML
    private Label lblCash;

    /**
     * The label telling the player the amount of open slots in their inventory.
     */
    @FXML
    private Label lblSlots;

    /**
     * The label that will contain error text if the player attempts to
     * perform an illegal action.
     */
    @FXML
    private Label errorLabel;

    /**
     * The list of goods the player can buy from the trader.
     */
    @FXML
    private ListView<BuyBar> buyGoods;

    /**
     * The list of goods that the player can sell to the trader.
     */
    @FXML
    private ListView<BuyBar> sellGoods;

    /**
     * The trader the player will be interacting with.
     */
    private final Trader trader;
    /**
     * The path that the constructor will take.
     */
    public static final String MARKET_VIEW_FILE = "/fxml/trader.fxml";

    /**
     * Construct the trader controller.
     *
     * @param t 
     */
    public TraderController(Trader t) {
        // load the view or throw an exception
        super(MARKET_VIEW_FILE);

        this.trader = t;

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
        Transaction t = new Transaction(player, this.trader);

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
        Transaction t = new Transaction(player, this.trader);

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

    /**
     * Fills the labels showing the player's money supply, open bays in their
     * ship, and the default errorLabel text.
     */
    private void fillLabels() {
        this.lblCash.setText(((Integer) player.getMoney()).toString());
        Ship s = player.getShip();
        this.lblSlots.setText(Integer.toString(s.getOpenBays()));
        errorLabel.setText("");
    }

    /**
     * Builds the list of goods that can be bought from the trader.
     */
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

    /**
     * Builds the list of goods that can be sold from the items in the player's
     * inventory.
     */
    private void buildSellGoodsList() {
        ObservableList<BuyBar> listGoods = FXCollections.observableArrayList();
        Map<String, Integer> playerGoods = player.getShip().getCargoList();
        Map<String, Integer> prices = trader.getDemand(playerGoods.keySet());
        for (Map.Entry<String, Integer> s : playerGoods.entrySet()) {
            if (s.getValue() == 0) {
                continue; // don't bother with these
            }
            GoodType g = (GoodType) Data.GOODS.get(s.getKey());
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
