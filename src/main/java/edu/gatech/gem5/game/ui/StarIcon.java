package edu.gatech.gem5.game.ui;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.Font;

import edu.gatech.gem5.game.SolarSystem;

/**
 * An icon representing a solar system in the universe map.
 *
 * @author Creston Bunch
 */
public class StarIcon extends Region {

    private ImageView image;

    /**
     * Construct and set the root of this custom control.
     */
    public StarIcon(SolarSystem sys) {
        image = new ImageView(sys.getType().getUniverseImage());
        image.fitWidthProperty().bind(this.widthProperty());
        image.fitHeightProperty().bind(this.heightProperty());
        setPrefWidth(sys.getType().getSize());
        setPrefHeight(sys.getType().getSize());
        getChildren().add(image);

        GridPane content = new GridPane();
        content.setHgap(5);
        content.setVgap(5);

        Map<String, String> attrs = new HashMap();
        attrs.put("Planets", Integer.toString(sys.getPlanets().size()));

        int row = 0;
        for (Map.Entry<String, String> e : attrs.entrySet()) {
            Label key = new Label(e.getKey());
            Label val = new Label(e.getValue());
            content.setRowIndex(key, row);
            content.setColumnIndex(key, 0);
            content.setRowIndex(val, row);
            content.setColumnIndex(val, 1);
            content.getChildren().add(key);
            content.getChildren().add(val);
            row++;
        }

        HoverBox b = new HoverBox();
        b.addNode(new Label(sys.getName()));
        b.addNode(content);
        b.bind(image);

    }

}
