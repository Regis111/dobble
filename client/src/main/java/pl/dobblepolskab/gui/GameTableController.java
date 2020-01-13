package pl.dobblepolskab.gui;

import gamecontent.DifficultyLevel;
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
import pl.dobblepolskab.gui.events.SingleplayerEndedEvent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GameTableController {
    private static final double GAP = 10.0;
    private static final int COUNTDOWN_END = -3600000;

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

    private final DateFormat timeFormat = new SimpleDateFormat("mm:ss" );

    private DifficultyLevel level;
    private IntegerProperty score = new SimpleIntegerProperty(0);
    private long duration = 8000;

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
                if (time.getTime() == COUNTDOWN_END) {
                    timeline.stop();
                    backToMenu();
                }
                time.setTime(time.getTime() - 1000); // 1000ms = 1s
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
        switch (level) {
            case Easy:
                duration = 8000;
                break;
            case Medium:
                duration = 5000;
                break;
            case Hard:
                duration = 3000;
                break;
            case Expert:
                duration = 2000;
                break;
        }
        timeDisplay.setText(timeFormat.format(duration));
    }

    private void backToMenu() {
        scene.getRoot().fireEvent(new SingleplayerEndedEvent(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, "SaveResult.fxml", level, score.get()));
    }
}
