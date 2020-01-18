import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pl.dobblepolskab.gui.controllers.GameTableController;
import pl.dobblepolskab.gui.controllers.LoadingMenuController;
import pl.dobblepolskab.gui.controllers.SaveResultController;
import pl.dobblepolskab.gui.events.*;
import websocket.ServerSDK;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private Scene currentScene;

    private FXMLLoader loader;
    private boolean settingHandlerNeeded = true;

    // Multiplayer game settings.
    private ServerSDK connection;
    private boolean isAdmin;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        currentScene = loader.load();
        primaryStage.setScene(currentScene);
        primaryStage.show();

        // Makes sure the client (if connected) disconnects before leaving the game.
        primaryStage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, e -> {
            if (loader.getController() instanceof GameTableController)
                ((GameTableController) loader.getController()).endTheGame();
            Platform.exit();
        });

        setHandlers();
    }

    private void loadAndLayoutNewScene() {
        try {
            final double currentWidth = currentScene.getWidth();
            final double currentHeight = currentScene.getHeight();

            currentScene = loader.load();

            /*
             * I have decided to leave those magic numbers because that is what they are - magic numbers. The size
             * of the window decreases by those values with a scene change (at least in the cases I have tested) and
             * I do not know why.
             */
            primaryStage.setWidth(currentWidth + 6);
            primaryStage.setHeight(currentHeight + 32);

            // If we take out the 'runLater' function and execute normally, threading errors appear.
            Platform.runLater(() -> {
                primaryStage.setScene(currentScene);
                primaryStage.show();
            });

            if (settingHandlerNeeded) {
                settingHandlerNeeded = false;
                setHandlers();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHandlers() {
        currentScene.getRoot().addEventHandler(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, e -> {
            e.consume();

            final String newSceneUrl = e.getNewSceneUrl();
            loader = new FXMLLoader(getClass().getResource(newSceneUrl));

            loadAndLayoutNewScene();
        });

        currentScene.getRoot().addEventHandler(GameStartedEvent.SINGLEPLAYER_STARTED_EVENT_TYPE, e -> {
            e.consume();

            loader = new FXMLLoader(getClass().getResource("GameTable.fxml"));
            GameTableController controller = new GameTableController(e.getDifficultyLevel());
            loader.setController(controller);
            loadAndLayoutNewScene();
        });

        currentScene.getRoot().addEventHandler(GameStartedEvent.MULTIPLAYER_STARTED_EVENT_TYPE, e -> {
            e.consume();

            loader = new FXMLLoader(getClass().getResource("LoadingMenu.fxml"));
            LoadingMenuController controller = new LoadingMenuController(e.getDifficultyLevel(), e.getBotsCount(), e.getDuration());
            loader.setController(controller);

            loadAndLayoutNewScene();
        });

        currentScene.getRoot().addEventHandler(InitializationFinishedEvent.INITIALIZATION_FINISHED_EVENT_TYPE, e -> {
            e.consume();

            loader = new FXMLLoader(getClass().getResource("GameTable.fxml"));
            GameTableController controller = new GameTableController(e.getConnection(), e.getCards(), e.getGameTimeInMinutes());
            this.connection = e.getConnection();
            this.isAdmin = e.isAdmin();
            loader.setController(controller);

            loadAndLayoutNewScene();
        });

        currentScene.getRoot().addEventHandler(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, e -> {
            e.consume();

            loader = new FXMLLoader(getClass().getResource("SaveResult.fxml"));
            SaveResultController controller = new SaveResultController(e.getDifficultyLevel(), e.getScore());
            loader.setController(controller);

            loadAndLayoutNewScene();
        });

        currentScene.getRoot().addEventHandler(MultiplayerEndedEvent.MULTIPLAYER_ENDED_EVENT_TYPE, e -> {
            e.consume();

            final String clientId = connection.getStompSessionHandler().getClientID();
            connection.deletePlayer(clientId, clientId);
            if (isAdmin)
                connection.endGameSession();
            connection.getSession().disconnect();

            loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

            loadAndLayoutNewScene();
        });

        settingHandlerNeeded = true;
    }
}
