package pl.dobblepolskab.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.dobblepolskab.gui.events.SceneChangedEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class LeaderboardsController {

    @FXML
    private Scene scene;
    @FXML
    private Button goBack;
    @FXML
    private TableView<GameResult> table;
    @FXML
    private TableColumn<GameResult, String> dateColumn;
    @FXML
    private TableColumn<GameResult, String> nameColumn;
    @FXML
    private TableColumn<GameResult, String> levelColumn;
    @FXML
    private TableColumn<GameResult, String> scoreColumn;

    @FXML
    public void initialize() {
        goBack.prefWidthProperty().bind(scene.widthProperty().multiply(0.3));

        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        table.setItems(readGameResults("RESULTS.TXT"));
    }

    private ObservableList<GameResult> readGameResults(String filePath) {
        ObservableList<GameResult> results = FXCollections.observableArrayList();

        try (FileReader fr = new FileReader(filePath)) {
            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();
            String date, name, level;
            int score;
            while (line != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ";");

                if (tokenizer.countTokens() == 4) {
                    date = tokenizer.nextToken();
                    name = tokenizer.nextToken();
                    level = tokenizer.nextToken();
                    score = Integer.parseInt(tokenizer.nextToken());
                    results.add(new GameResult(date, name, level, score));
                }

                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }
}
