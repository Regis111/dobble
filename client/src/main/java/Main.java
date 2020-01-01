import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.dobblepolskab.gui.DobbleGameController;
import pl.dobblepolskab.gui.SceneChangedEvent;
import pl.dobblepolskab.gui.SingleplayerStartedEvent;

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

        loader = new FXMLLoader(getClass().getResource("DobbleMenu.fxml"));
        currentScene = loader.load();
        primaryStage.setScene(currentScene);
        primaryStage.show();

        setSceneChangeHandler();
    }

    private void setSceneChangeHandlerCommon(SceneChangedEvent sceneChangedEvent) {
        try {
            final double currentWidth = currentScene.getWidth();
            final double currentHeight = currentScene.getHeight();

            final String newSceneUrl = sceneChangedEvent.getNewSceneUrl();

            loader = new FXMLLoader(getClass().getResource(newSceneUrl));
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

            sceneChangedEvent.consume();

            if (settingHandlerNeeded) {
                settingHandlerNeeded = false;
                setSceneChangeHandler();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSceneChangeHandler() {
        currentScene.getRoot().addEventHandler(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, this::setSceneChangeHandlerCommon);
        currentScene.getRoot().addEventHandler(SingleplayerStartedEvent.SINGLEPLAYER_STARTED_EVENT_TYPE, singleplayerStartedEvent -> {
            setSceneChangeHandlerCommon(singleplayerStartedEvent);
            DobbleGameController controller = loader.getController();
            controller.setDifficultyLevel(singleplayerStartedEvent.getDifficultyLevel());
        });

        settingHandlerNeeded = true;
    }
}
