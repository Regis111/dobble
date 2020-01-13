package pl.dobblepolskab.gui;

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
    private Button saveButton;

    private DifficultyLevel level;
    private long score = 0;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        DoubleBinding fontSize = width.add(height).divide(40);
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        nameField.prefWidthProperty().bind(width.multiply(0.15));
        saveButton.prefWidthProperty().bind(width.multiply(0.15));
    }

    @FXML
    public void saveResult() {
        try (FileWriter fw = new FileWriter("RESULTS.TXT", true)) {
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

    public void setDifficultyLevel(DifficultyLevel level) {
        this.level = level;
    }

    public void setScore(long score) {
        this.score = score;
        scoreDisplay.setText("Score: " + score);
    }
}
