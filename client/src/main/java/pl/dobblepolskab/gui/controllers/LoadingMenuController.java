package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import messages.responses.InitResponse;
import pl.dobblepolskab.gui.others.LayoutConstants;
import pl.dobblepolskab.gui.others.Timer;
import pl.dobblepolskab.gui.events.InitializationFinishedEvent;
import pl.dobblepolskab.gui.events.SceneChangedEvent;
import pl.dobblepolskab.gui.events.ServerRespondedEvent;
import pl.dobblepolskab.gui.events.TimerFinishedEvent;
import websocket.ServerSDK;

import java.util.concurrent.ExecutionException;

public class LoadingMenuController {
    private static final double CENTRAL_ITEMS_POSITION_MULTIPLIER = 2.6;
    private static final int WAITING_TIME_SECONDS = 30;

    @FXML
    private Scene scene;
    @FXML
    private VBox vBox;
    @FXML
    private Timer timeDisplay;
    @FXML
    private Text messageDisplay;

    private boolean isAdmin;
    private DifficultyLevel level;
    private int botsCount;
    private int gameTimeInMinutes;

    private ServerSDK connection;
    private boolean isInitialized;

    public LoadingMenuController(DifficultyLevel level, int botsCount, int gameTimeInMinutes) {
        this.level = level;
        this.botsCount = botsCount;
        this.gameTimeInMinutes = gameTimeInMinutes;
        this.isInitialized = false;

        this.isAdmin = !(botsCount == -1);
    }

    private void setLayout() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();

        vBox.spacingProperty().bind(height.divide(CENTRAL_ITEMS_POSITION_MULTIPLIER));

        DoubleBinding fontSize = width.add(height).divide(LayoutConstants.FONT_SIZE_MULTIPLIER);
        timeDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
        messageDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
    }

    // If the timer finishes, user goes back to the main menu.
    private void startTimer() {
        timeDisplay.addEventHandler(TimerFinishedEvent.TIMER_FINISHED_EVENT_TYPE, event -> {
            if (!isInitialized)
                goBackToMenu();
        });
        timeDisplay.run(WAITING_TIME_SECONDS);
    }

    private void establishConnection() throws ExecutionException, InterruptedException {
        scene.getRoot().addEventHandler(ServerRespondedEvent.INITIALIZATION_MESSAGE_EVENT_TYPE, event -> {
            InitResponse response = (InitResponse) event.getResponse();
            final int[] cards = { response.getFirstCard() + 1, response.getSecondCard() + 1 };

            isInitialized = true;
            InitializationFinishedEvent e =
                    new InitializationFinishedEvent(InitializationFinishedEvent.INITIALIZATION_FINISHED_EVENT_TYPE,
                            connection, cards, isAdmin, response.getGameTimeInMinutes());
            scene.getRoot().fireEvent(e);
        });

        connection = new ServerSDK(scene.getRoot());
        String clientId = connection.getStompSessionHandler().getClientID();
        if (isAdmin)
            connection.initSessionAsAdmin(clientId, level, botsCount, gameTimeInMinutes);
    }

    @FXML
    public void initialize() {
        try {
            setLayout();
            startTimer();
            establishConnection();
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Failed to establish a connection with the server");
            goBackToMenu();
        }
    }

    private void goBackToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
    }
}
