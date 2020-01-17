package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import pl.dobblepolskab.gui.events.SceneChangedEvent;
import pl.dobblepolskab.gui.others.GameConstants;
import pl.dobblepolskab.gui.others.LayoutConstants;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveResultController {

    @FXML
    private Scene scene;
    @FXML
    private Text scoreDisplay;
    @FXML
    private TextField nameField;
    @FXML
    private Button goBack;
    @FXML
    private Button saveButton;

    private DifficultyLevel level;
    private long score;

    public SaveResultController(DifficultyLevel level, long score) {
        this.level = level;
        this.score = score;
    }

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        DoubleBinding fontSize = width.add(height).divide(LayoutConstants.FONT_SIZE_MULTIPLIER);
        scoreDisplay.setText("Score: " + score);
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        nameField.prefWidthProperty().bind(width.multiply(LayoutConstants.MIDDLE_BUTTON_WIDTH_MULTIPLIER));
        goBack.prefWidthProperty().bind(width.multiply(LayoutConstants.MIDDLE_BUTTON_WIDTH_MULTIPLIER));
        saveButton.prefWidthProperty().bind(width.multiply(LayoutConstants.MIDDLE_BUTTON_WIDTH_MULTIPLIER));
    }

    @FXML
    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }

    @FXML
    public void saveResult() {
        try (FileWriter fw = new FileWriter(GameConstants.RESULTS_FILE, true)) {
            BufferedWriter writer = new BufferedWriter(fw);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date currentDate = new Date();
            writer.append(dateFormat.format(currentDate))
                  .append(";").append(nameField.getText())
                  .append(";").append(level.toString())
                  .append(";").append(String.valueOf(score));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }
}
