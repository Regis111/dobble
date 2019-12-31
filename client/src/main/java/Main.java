import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.dobblepolskab.gui.SceneChangeEvent;

import java.io.IOException;

public class Main extends Application {
    private Stage primaryStage;
    private Scene currentScene;

    private boolean settingHandlerNeeded = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DobbleMenu.fxml"));
        currentScene = loader.load();
        primaryStage.setScene(currentScene);
        primaryStage.show();

        setSceneChangeHandler();

        /*
        Pane primaryPane = new Pane();
        DobbleGameScene dobbleWindow = new DobbleGameScene(primaryPane, 800, 600, Color.LIGHTGREEN);
        DobbleMenuScene dobbleMenuScene = new DobbleMenuScene(primaryPane, 800, 600);
        primaryStage.setTitle("Dobble");
        primaryStage.setScene(dobbleMenuScene);
        primaryStage.show();
        */
    }

    private void setSceneChangeHandler() {
        currentScene.getRoot().addEventHandler(SceneChangeEvent.SCENE_CHANGE_EVENT_TYPE, sceneChangeEvent -> {
            try {
                final double currentWidth = currentScene.getWidth();
                final double currentHeight = currentScene.getHeight();

                final String newSceneUrl = sceneChangeEvent.getNewSceneUrl();

                FXMLLoader newSceneLoader = new FXMLLoader(getClass().getResource(newSceneUrl));
                currentScene = newSceneLoader.load();

                /*
                 * I have decided to leave those magic numbers because that is what they are - magic numbers. The size
                 * of the window decreases by those values with a scene change (at least in the cases I have tested) and
                 * I do not know why.
                 */
                primaryStage.setWidth(currentWidth + 6);
                primaryStage.setHeight(currentHeight + 32);

                primaryStage.setScene(currentScene);
                primaryStage.show();

                sceneChangeEvent.consume();

                if (settingHandlerNeeded) {
                    settingHandlerNeeded = false;
                    setSceneChangeHandler();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        settingHandlerNeeded = true;
    }
}
