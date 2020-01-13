import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.dobblepolskab.gui.*;
import pl.dobblepolskab.gui.events.*;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private Scene currentScene;

    private FXMLLoader loader;
    private boolean settingHandlerNeeded = true;

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

            primaryStage.setScene(currentScene);
            primaryStage.show();

            if (settingHandlerNeeded) {
                settingHandlerNeeded = false;
                setHandlers();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSceneChangedHandlerCommon(SceneChangedEvent sceneChangedEvent) {
        final String newSceneUrl = sceneChangedEvent.getNewSceneUrl();
        loader = new FXMLLoader(getClass().getResource(newSceneUrl));

        sceneChangedEvent.consume();
        loadAndLayoutNewScene();
    }

    private void setGameStartedHandlerCommon(GameTableController controller) {
        loader = new FXMLLoader(getClass().getResource("GameTable.fxml"));
        loader.setController(controller);

        loadAndLayoutNewScene();
    }

    private void setHandlers() {
        currentScene.getRoot().addEventHandler(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, this::setSceneChangedHandlerCommon);
        currentScene.getRoot().addEventHandler(GameStartedEvent.SINGLEPLAYER_STARTED_EVENT_TYPE, event -> {
            event.consume();

            GameTableController controller = new GameTableController(GameType.SINGLEPLAYER, event.getDifficultyLevel());
            setGameStartedHandlerCommon(controller);
        });
        currentScene.getRoot().addEventHandler(GameStartedEvent.MULTIPLAYER_STARTED_EVENT_TYPE, event -> {
            event.consume();

            GameTableController controller = new GameTableController(GameType.MULTIPLAYER, event.getDifficultyLevel());
            setGameStartedHandlerCommon(controller);
        });
        currentScene.getRoot().addEventHandler(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, singleplayerEndedEvent -> {
            setSceneChangedHandlerCommon(singleplayerEndedEvent);
            SaveResultController controller = loader.getController();
            controller.setDifficultyLevel(singleplayerEndedEvent.getDifficultyLevel());
            controller.setScore(singleplayerEndedEvent.getScore());
        });

        settingHandlerNeeded = true;
    }
}
