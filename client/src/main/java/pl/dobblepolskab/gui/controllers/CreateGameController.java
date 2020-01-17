package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.VBox;
import pl.dobblepolskab.gui.events.GameStartedEvent;
import pl.dobblepolskab.gui.events.SceneChangedEvent;

import java.util.Arrays;
import java.util.List;

public class CreateGameController {

    @FXML
    private Scene scene;
    @FXML
    private VBox vBox;
    @FXML
    private ComboBox<Integer> playersCount;
    @FXML
    private ComboBox levelInput;
    @FXML
    private Spinner timeInput;
    @FXML
    private Button goBack;
    @FXML
    private Button play;

    private int count = 1;
    private DifficultyLevel level = DifficultyLevel.Easy;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        vBox.spacingProperty().bind(height.divide(2.3));

        ObservableList<Integer> items = FXCollections.observableArrayList();
        for (int i = 0; i < 7; i++)
            items.add(i + 1);
        playersCount.setItems(items);

        List<Button> buttons = Arrays.asList(goBack, play);
        for (Button button : buttons)
            button.prefWidthProperty().bind(width.multiply(0.3));
    }

    @FXML
    public void botsCountSelected() {
        count = playersCount.getValue();
    }

    @FXML
    public void difficultyLevelSelected() {
        level = DifficultyLevel.valueOf((String) levelInput.getValue());
    }

    @FXML
    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MultiplayerSettings.fxml"));
    }

    @FXML
    public void startTheGame() {
        scene.getRoot().fireEvent(new GameStartedEvent(GameStartedEvent.MULTIPLAYER_STARTED_EVENT_TYPE, level, count, (int) timeInput.getValue()));
    }
}
