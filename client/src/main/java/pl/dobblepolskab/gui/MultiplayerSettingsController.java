package pl.dobblepolskab.gui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pl.dobblepolskab.gui.events.SceneChangedEvent;

import java.util.Arrays;
import java.util.List;


public class MultiplayerSettingsController {

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

        vBox.spacingProperty().bind(height.divide(2.6));

        List<Button> buttons = Arrays.asList(createGame, joinGame);
        for (Button button : buttons) {
            button.prefWidthProperty().bind(width.multiply(0.3));
            button.prefHeightProperty().bind(height.multiply(0.07));
        }

        goBack.prefWidthProperty().bind(width.multiply(0.3));
    }

    @FXML
    public void createGame() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "CreateGame.fxml"));
    }

    @FXML
    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }
}
