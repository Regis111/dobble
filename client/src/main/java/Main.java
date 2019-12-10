import pl.dobblepolskab.gui.DobbleWindow;

import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane primaryPane = new Pane();
        DobbleWindow dobbleWindow = new DobbleWindow(primaryPane, 800, 600, Color.LIGHTGREEN);
        primaryStage.setTitle("Dobble");
        primaryStage.setScene(dobbleWindow);
        primaryStage.show();
    }
}
