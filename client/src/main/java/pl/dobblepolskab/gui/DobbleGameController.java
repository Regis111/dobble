package pl.dobblepolskab.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.beans.EventHandler;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DobbleGameController {
    private static final double GAP = 10.0;

    @FXML
    private Scene scene;
    @FXML
    private Pane pane;
    @FXML
    private DobbleCard leftCard;
    @FXML
    private DobbleCard rightCard;
    @FXML
    private Text scoreDisplay;
    @FXML
    private Text timeDisplay;

    private IntegerProperty score = new SimpleIntegerProperty(0);

    private final DateFormat timeFormat = new SimpleDateFormat("mm:ss" );

    private long duration = 5000;
    private DifficultyLevel level;

    @FXML
    public void initialize() {
        layoutItems();

        scene.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            DobbleImage leftCardImage = leftCard.getHighlightedImage();
            DobbleImage rightCardImage = rightCard.getHighlightedImage();

            if (leftCardImage != null && rightCardImage != null) {
                String leftCardImagePath = leftCardImage.getPath();
                String rightCardImagePath = rightCardImage.getPath();
                if (leftCardImagePath.equals(rightCardImagePath)) {
                    score.set(score.get() + 1);
                    //scene.getRoot().fireEvent(new PairFoundEvent(PairFoundEvent.PAIR_FOUND_EVENT_TYPE));
                    refresh();
                }
            }
        });

        setImages();

        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            try {
                Date time = timeFormat.parse(timeDisplay.getText());
                if (time.getTime() == -3600000) {
                    timeline.stop();
                    backToMenu();
                }
                time.setTime(time.getTime() - 1000);
                timeDisplay.setText(timeFormat.format(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private DobbleImage[] generateImages() {
        List<String> urls = Arrays.asList("file:images/dolphin.png", "file:images/heart.png", "file:images/crayon.png");
        DobbleImage[] images = new DobbleImage[8];
        for (int i = 0; i < 8; i++) {
            int r = ThreadLocalRandom.current().nextInt(0, 3);
            images[i] = new DobbleImage(i + 1, urls.get(r));
        }
        return images;
    }

    private void setImages() {
        leftCard.setImages(generateImages());
        rightCard.setImages(generateImages());
    }

    private void refresh() {
        pane.getChildren().removeAll(leftCard, rightCard);
        leftCard.removeHighlight();

        rightCard = leftCard;

        leftCard = new DobbleCard();
        leftCard.setImages(generateImages());

        layoutItems();
        pane.getChildren().addAll(leftCard, rightCard);
    }

    private void layoutItems() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        ReadOnlyDoubleProperty height = scene.heightProperty();
        DoubleBinding cardHeight = scene.heightProperty().divide(2);
        DoubleBinding cardRadius = scene.widthProperty().subtract(GAP * 4).divide(4);

        leftCard.getRadiusProperty().bind(cardRadius);
        leftCard.getCenterXProperty().bind(cardRadius.add(GAP));
        leftCard.getCenterYProperty().bind(cardHeight);

        rightCard.getRadiusProperty().bind(cardRadius);
        rightCard.getCenterXProperty().bind(width.subtract(cardRadius).subtract(GAP));
        rightCard.getCenterYProperty().bind(cardHeight);

        DoubleBinding fontSize = width.add(height).divide(40);

        scoreDisplay.layoutYProperty().bind(height.multiply(0.99));
        scoreDisplay.textProperty().bind(Bindings.createStringBinding(() -> "Score: " + score.get(), score));
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        timeDisplay.layoutXProperty().bind(width.multiply(0.865));
        timeDisplay.layoutYProperty().bind(fontSize);
        timeDisplay.setText(timeFormat.format(duration));
        timeDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
    }

    public void setDifficultyLevel(DifficultyLevel level) {
        this.level = level;
    }

    private void backToMenu() {
        scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "DobbleMenu.fxml"));
    }
}
