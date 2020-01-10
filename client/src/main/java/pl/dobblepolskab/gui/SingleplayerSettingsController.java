package pl.dobblepolskab.gui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import pl.dobblepolskab.gui.events.SceneChangedEvent;
import pl.dobblepolskab.gui.events.SingleplayerStartedEvent;

import java.util.Arrays;
import java.util.List;

public class SingleplayerSettingsController {

    @FXML
    private Scene scene;
    @FXML
    private VBox vBox;
    @FXML
    private ComboBox levelInput;
    @FXML
    private Button goBack;
    @FXML
    private Button play;

    private DifficultyLevel level = DifficultyLevel.Easy;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        vBox.spacingProperty().bind(height.divide(2.3));

        List<Button> buttons = Arrays.asList(goBack, play);
        for (Button button : buttons)
            button.prefWidthProperty().bind(width.multiply(0.3));
    }

    public void difficultyLevelSelected(ActionEvent actionEvent) {
        level = DifficultyLevel.valueOf((String) levelInput.getValue());
    }

    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }

    public void startTheGame() {
        scene.getRoot().fireEvent(new SingleplayerStartedEvent(SingleplayerStartedEvent.SINGLEPLAYER_STARTED_EVENT_TYPE, "GameTable.fxml", level));
    }
}
