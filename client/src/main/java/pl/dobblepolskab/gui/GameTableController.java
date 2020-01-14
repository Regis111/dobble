package pl.dobblepolskab.gui;

import gamecontent.DifficultyLevel;
import gamecontent.GameContent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import messages.responses.AmIWinnerResponse;
import messages.responses.InitResponse;
import pl.dobblepolskab.gui.events.ServerRespondedEvent;
import pl.dobblepolskab.gui.events.SingleplayerEndedEvent;
import websocket.ServerSDK;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class GameTableController {
    private static final double GAP = 10.0;
    private static final int COUNTDOWN_END = -3600000;
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("mm:ss" );

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

    // This fields are used in both singleplayer & multiplayer modes.
    private final GameType type;
    private IntegerProperty score;
    private DifficultyLevel level;
    private int turnId = 1;

    // This field is used only in the singleplayer mode.
    private long duration;

    // This fields are used only in the multiplayer mode.
    private ServerSDK server;
    private String clientId;
    private GameContent gameContent = GameContent.getInstance();

    public GameTableController(GameType type, DifficultyLevel level) {
        this.type = type;
        this.level = level;
        this.score = new SimpleIntegerProperty(0);
        //this.turnId = 0;

        switch (level) {
            case Easy:
                duration = 10000;
                break;
            case Medium:
                duration = 7000;
                break;
            case Hard:
                duration = 5000;
                break;
            case Expert:
                duration = 4000;
                break;
        }
    }

    @FXML
    public void initialize() throws ExecutionException, InterruptedException {
        /*
         * The connection with the server has to be established here as the scene object is not yet initialized while
         * in the constructor.
         */
        if (type == GameType.MULTIPLAYER)
            initializeServerConnection();

        layoutItems();

        scene.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            DobbleImage leftCardImage = leftCard.getHighlightedImage();
            DobbleImage rightCardImage = rightCard.getHighlightedImage();

            if (leftCardImage != null && rightCardImage != null) {
                String leftCardImagePath = leftCardImage.getPath();
                String rightCardImagePath = rightCardImage.getPath();
                final boolean isCorrectPair = leftCardImagePath.equals(rightCardImagePath);
                if (isCorrectPair && type == GameType.SINGLEPLAYER) {
                    score.set(score.get() + 1);
                    refresh();
                } else if (isCorrectPair && type == GameType.MULTIPLAYER) {
                    server.askIfWonShout(clientId, turnId);
                    turnId++;
                }
            }
        });

        if (type == GameType.SINGLEPLAYER)
            setImages();

        Timeline timeline = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), event -> {
            try {
                Date time = TIME_FORMAT.parse(timeDisplay.getText());
                if (time.getTime() == COUNTDOWN_END) {
                    timeline.stop();
                    backToMenu();
                }
                time.setTime(time.getTime() - 1000); // 1000ms = 1s
                timeDisplay.setText(TIME_FORMAT.format(time));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void initializeServerConnection() throws ExecutionException, InterruptedException {
        final CountDownLatch latch = new CountDownLatch(1);

        scene.getRoot().addEventHandler(ServerRespondedEvent.INITIALIZATION_MESSAGE_EVENT_TYPE, event -> {
            InitResponse response = (InitResponse) event.getResponse();
            System.out.println(response.getFirstCard() + " " + response.getSecondCard());

            String[] leftCardImagePaths = gameContent.getGameCardSymbolPaths(response.getFirstCard() + 1);
            DobbleImage[] leftCardImages = new DobbleImage[8];
            for (int i = 0; i < 8; i++) {
                File f = new File(leftCardImagePaths[i]);
                leftCardImages[i] = new DobbleImage(i + 1, "file:images/" + f.getName());
            }
            String[] rightCardImagePaths = gameContent.getGameCardSymbolPaths(response.getSecondCard() + 1);
            DobbleImage[] rightCardImages = new DobbleImage[8];
            for (int i = 0; i < 8; i++) {
                File f = new File(rightCardImagePaths[i]);
                rightCardImages[i] = new DobbleImage(i + 1, "file:images/" + f.getName());
            }


            leftCard.setImages(leftCardImages);
            rightCard.setImages(rightCardImages);
            latch.countDown();
        });

        scene.getRoot().addEventHandler(ServerRespondedEvent.NEXT_TURN_STARTED_EVENT_TYPE, event -> {
            AmIWinnerResponse response = (AmIWinnerResponse) event.getResponse();
            System.out.println(response.getCard());

            String[] imagePaths = gameContent.getGameCardSymbolPaths(response.getCard() + 1);
            DobbleImage[] images = new DobbleImage[8];
            for (int i = 0; i < 8; i++) {
                File f = new File(imagePaths[i]);
                images[i] = new DobbleImage(i + 1, "file:images/" + f.getName());
            }

            Platform.runLater(() -> {
                refresh();
                leftCard.setImages(images);
                layoutItems();
                pane.getChildren().addAll(leftCard, rightCard);
            });
        });

        server = new ServerSDK(scene.getRoot());
        this.clientId = server.getStompSessionHandler().getClientID();
        server.initSessionAsAdmin(clientId, level, 2);

        // Wait for initialization
        latch.await();
    }

    private DobbleImage[] generateImages() {
        //List<String> urls = Arrays.asList("file:images/1s.png", "file:images/16s.png", "file:images/21s.png");
        final int c = ThreadLocalRandom.current().nextInt(1, 57);
        String[] imagePaths = gameContent.getGameCardSymbolPaths(c);
        DobbleImage[] images = new DobbleImage[8];
        for (int i = 0; i < 8; i++) {
            File f = new File(imagePaths[i]);
            images[i] = new DobbleImage(i + 1, "file:images/" + f.getName());
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
        if (type == GameType.SINGLEPLAYER) {
            leftCard.setImages(generateImages());

            layoutItems();
            setImages();
            pane.getChildren().addAll(leftCard, rightCard);
        }
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

        scoreDisplay.textProperty().bind(Bindings.createStringBinding(() -> "Score: " + score.get(), score));
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        timeDisplay.setText(TIME_FORMAT.format(duration));
        timeDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
    }

    private void backToMenu() {
        if (type == GameType.MULTIPLAYER) {
            server.deletePlayer(clientId, clientId);
            server.endGameSession();
            server.getSession().disconnect();
        }

        scene.getRoot().fireEvent(new SingleplayerEndedEvent(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, "SaveResult.fxml", level, score.get()));
    }
}
