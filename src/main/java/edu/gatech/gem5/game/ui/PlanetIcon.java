package edu.gatech.gem5.game.ui;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;

import edu.gatech.gem5.game.Planet;

/**
 * An icon representing a planet in the universe map.
 *
 * @author Creston Bunch
 */
public class PlanetIcon extends Pane {

    private Ellipse shape;

    /**
     * No arg constructor
     */
    public PlanetIcon() {
        shape = new Ellipse();
        shape.radiusXProperty().bind(this.widthProperty().divide(2));
        shape.radiusYProperty().bind(this.heightProperty().divide(2));
        shape.centerXProperty().bind(this.widthProperty().divide(2));
        shape.centerYProperty().bind(this.heightProperty().divide(2));
        shape.setFill(Color.GREEN);
        getChildren().add(shape);
    }

    /**
     * Construct and set the root of this custom control.
     */
    public PlanetIcon(Planet p) {
        this();
        setCursor(Cursor.HAND);
        setPrefWidth(10);
        setPrefHeight(10);

        Text t = new Text(p.getName());
        t.setFont(new Font(5));
        t.setFill(Color.WHITE);
        t.setTextAlignment(TextAlignment.CENTER);
        t.setY(-1);
        t.xProperty().bind(this.widthProperty().divide(-2));
        getChildren().add(t);

        GridPane content = new GridPane();
        content.setHgap(5);
        content.setVgap(5);

        Map<String, String> attrs = new HashMap();
        attrs.put("Technology", p.getTechLevel().getName());
        attrs.put("Government", p.getGovernment().getName());
        attrs.put("Environment", p.getEnvironment().getName());
        attrs.put("Condition", p.getCondition().getName());

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
        b.addNode(content);
        b.bind(shape);
    }

}
