package pl.dobblepolskab.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.Arrays;
import java.util.List;

public class DobbleMenuController {
    private final static double Y_GAP = 15.0;

    @FXML
    private Scene scene;
    @FXML
    private ImageView logo;
    @FXML
    private Button singleplayer;
    @FXML
    private Button multiplayer;
    @FXML
    private Button settings;
    @FXML
    private Button exit;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty widthProperty = scene.widthProperty();
        ReadOnlyDoubleProperty heightProperty = scene.heightProperty();

        DoubleBinding paneHalfWidth = widthProperty.divide(2);
        DoubleBinding paneHalfHeight = heightProperty.divide(2);

        // Position the logo
        logo.fitWidthProperty().bind(widthProperty.multiply(0.7));

        DoubleBinding logoHalfWidth = logo.fitWidthProperty().divide(2);
        logo.xProperty().bind(paneHalfWidth.subtract(logoHalfWidth));
        logo.yProperty().set(Y_GAP * 2);

        // Position the buttons
        List<Button> buttons = Arrays.asList(singleplayer, multiplayer, settings, exit);
        double offset = Y_GAP;
        for (int i = 0; i < 4; i++) {
            Button current = buttons.get(i);

            current.prefWidthProperty().bind(widthProperty.multiply(0.3));
            current.prefHeightProperty().bind(heightProperty.multiply(0.07));

            DoubleBinding buttonHalfWidth = current.prefWidthProperty().divide(2);
            current.layoutXProperty().bind(paneHalfWidth.subtract(buttonHalfWidth));
            if (i == 0)
                current.layoutYProperty().bind(paneHalfHeight.add(offset));
            else {
                Button previous = buttons.get(i - 1);
                DoubleBinding yBottom = previous.layoutYProperty().add(previous.prefHeightProperty());
                current.layoutYProperty().bind(yBottom.add(Y_GAP));
            }

            offset += buttons.get(i).getPrefHeight() + (Y_GAP / 2);
        }
    }

    @FXML
    public void startSingleplayer() {
        scene.getRoot().fireEvent(new SceneChangeEvent(SceneChangeEvent.SCENE_CHANGE_EVENT_TYPE, "DobbleGame.fxml"));
    }
}
