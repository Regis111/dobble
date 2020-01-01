package pl.dobblepolskab.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class DobbleSingleplayerController {
    private static final double X_GAP = 20.0;
    private static final double Y_GAP = 10.0;

    @FXML
    private Scene scene;
    @FXML
    private ComboBox levelInput;
    @FXML
    private Button goBack;
    @FXML
    private Button play;

    private DifficultyLevel level = DifficultyLevel.Easy;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty widthProperty = scene.widthProperty();
        ReadOnlyDoubleProperty heightProperty = scene.heightProperty();

        DoubleBinding paneHalfWidth = widthProperty.divide(2);
        DoubleBinding paneHalfHeight = heightProperty.divide(2);

        DoubleBinding levelInputHalfWidth = levelInput.widthProperty().divide(2);
        DoubleBinding levelInputHalfHeight = levelInput.heightProperty().divide(2);

        levelInput.layoutXProperty().bind(paneHalfWidth.subtract(levelInputHalfWidth));
        levelInput.layoutYProperty().bind(paneHalfHeight.subtract(levelInputHalfHeight));

        DoubleBinding buttonWidth = widthProperty.multiply(0.3);
        ReadOnlyDoubleProperty buttonHeight = levelInput.prefHeightProperty();

        goBack.prefWidthProperty().bind(buttonWidth);
        goBack.prefHeightProperty().bind(buttonHeight);

        play.prefWidthProperty().bind(buttonWidth);
        play.prefHeightProperty().bind(buttonHeight);

        goBack.layoutXProperty().bind(paneHalfWidth.subtract(buttonWidth).subtract(X_GAP));
        play.layoutXProperty().bind(paneHalfWidth.add(X_GAP));

        DoubleBinding buttonY = heightProperty.subtract(buttonHeight).subtract(Y_GAP);

        goBack.layoutYProperty().bind(buttonY);
        play.layoutYProperty().bind(buttonY);
    }

    public void difficultyLevelSelected(ActionEvent actionEvent) {
        level = DifficultyLevel.valueOf((String) levelInput.getValue());
    }

    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "DobbleMenu.fxml"));
    }

    public void startTheGame() {
        scene.getRoot().fireEvent(new SingleplayerStartedEvent(SingleplayerStartedEvent.SINGLEPLAYER_STARTED_EVENT_TYPE, "DobbleGame.fxml", level));
    }
}
