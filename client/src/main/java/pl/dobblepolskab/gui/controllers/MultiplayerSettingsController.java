package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pl.dobblepolskab.gui.events.GameStartedEvent;
import pl.dobblepolskab.gui.events.SceneChangedEvent;
import pl.dobblepolskab.gui.others.LayoutConstants;

import java.util.Arrays;
import java.util.List;


public class MultiplayerSettingsController {
    private static final double CENTRAL_ITEMS_POSITION_MULTIPLIER = 2.6;

    @FXML
    private Scene scene;
    @FXML
    private VBox vBox;
    @FXML
    private Button createGame;
    @FXML
    private Button joinGame;
    @FXML
    private Button goBack;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        vBox.spacingProperty().bind(height.divide(CENTRAL_ITEMS_POSITION_MULTIPLIER));

        List<Button> buttons = Arrays.asList(createGame, joinGame);
        for (Button button : buttons) {
            button.prefWidthProperty().bind(width.multiply(LayoutConstants.BOTTOM_BUTTON_WIDTH_MULTIPLIER));
            button.prefHeightProperty().bind(height.multiply(LayoutConstants.BUTTON_HEIGHT_MULTIPLIER));
        }

        goBack.prefWidthProperty().bind(width.multiply(LayoutConstants.BOTTOM_BUTTON_WIDTH_MULTIPLIER));
    }

    @FXML
    public void createGame() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "CreateGame.fxml"));
    }

    @FXML
    public void joinGame() {
        scene.getRoot().fireEvent(new GameStartedEvent(GameStartedEvent.MULTIPLAYER_STARTED_EVENT_TYPE, DifficultyLevel.Easy, -1, -1));
    }

    @FXML
    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }
}
