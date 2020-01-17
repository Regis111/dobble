package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
import gamecontent.GameContent;
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
import javafx.stage.WindowEvent;
import messages.responses.AmIWinnerResponse;
import messages.responses.ResponseType;
import pl.dobblepolskab.gui.DobbleCard;
import pl.dobblepolskab.gui.DobbleImage;
import pl.dobblepolskab.gui.others.GameType;
import pl.dobblepolskab.gui.others.Timer;
import pl.dobblepolskab.gui.events.SceneChangedEvent;
import pl.dobblepolskab.gui.events.ServerRespondedEvent;
import pl.dobblepolskab.gui.events.SingleplayerEndedEvent;
import pl.dobblepolskab.gui.events.TimerFinishedEvent;
import websocket.ServerSDK;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class GameTableController {
    private static final double GAP = 10.0;
    private static final int IMAGES_COUNT = 57;

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
    private Timer timeDisplay;

    // This fields are used in both singleplayer & multiplayer modes.
    private GameType type;
    private IntegerProperty score;

    // This field is used only in the singleplayer mode.
    private DifficultyLevel level;
    private long duration;

    // This fields are used only in the multiplayer mode.
    private ServerSDK connection;
    private String clientId;
    private int[] initialCards;
    private boolean isAdmin;
    private int turnId;

    public GameTableController(DifficultyLevel level) {
        this.type = GameType.SINGLEPLAYER;
        this.level = level;
        this.score = new SimpleIntegerProperty(0);
        this.isAdmin = false;

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

    public GameTableController(ServerSDK connection, int[] cards, boolean isAdmin, int gameDuration) {
        this.type = GameType.MULTIPLAYER;
        this.connection = connection;
        this.clientId = connection.getStompSessionHandler().getClientID();
        this.initialCards = cards;
        this.isAdmin = isAdmin;
        this.score = new SimpleIntegerProperty(0);
        this.turnId = 1;
        this.duration = gameDuration * 60 * 1000; // Conversion to milliseconds
    }

    @FXML
    public void initialize() {
        if (type == GameType.SINGLEPLAYER)
            initializeSingleplayer();
        else
            initializeMultiplayer();

        timeDisplay.addEventHandler(TimerFinishedEvent.TIMER_FINISHED_EVENT_TYPE, e -> {
            e.consume();

            endTheGame();
        });
        if (type == GameType.SINGLEPLAYER)
            timeDisplay.run(duration);
        else
            timeDisplay.run(duration);
    }

    private void initializeSingleplayer() {
        rightCard.setImages(loadRandomCard());
        updateTableCard(loadRandomCard(), false);

        scene.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            e.consume();

            if (isCorrectPair()) {
                score.set(score.get() + 1);
                updateTableCard(loadRandomCard(), true);
                timeDisplay.refresh();
            }
        });
    }

    private void initializeMultiplayer() {
        connection.getGameObject().addEventHandler(ServerRespondedEvent.NEXT_TURN_STARTED_EVENT_TYPE, e -> {
            e.consume();

            AmIWinnerResponse response = (AmIWinnerResponse) e.getResponse();

            final int newCardId = response.getCard() + 1;

            final boolean shift = (response.getResponseType() == ResponseType.WIN);
            if (shift)
                score.set(score.get() + 1);

            // If we take out the 'runLater' function and execute normally, threading errors appear.
            Platform.runLater(() -> updateTableCard(loadImagesForCard(newCardId), shift));
        });

        rightCard.setImages(loadImagesForCard(initialCards[1]));
        updateTableCard(loadImagesForCard(initialCards[0]), false);

        scene.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            e.consume();

            if (isCorrectPair()) {
                connection.askIfWonShout(clientId, turnId);
                turnId++;
            }
        });
    }

    private boolean isCorrectPair() {
        DobbleImage leftCardImage = leftCard.getHighlightedImage();
        DobbleImage rightCardImage = rightCard.getHighlightedImage();

        if (leftCardImage != null && rightCardImage != null) {
            String leftCardImagePath = leftCardImage.getPath();
            String rightCardImagePath = rightCardImage.getPath();
            return leftCardImagePath.equals(rightCardImagePath);
        }
        return false;
    }

    private DobbleImage[] loadImagesForCard(final int cardId) {
        final GameContent gameContent = GameContent.getInstance();

        String[] paths = gameContent.getGameCardSymbolPaths(cardId);
        DobbleImage[] images = new DobbleImage[DobbleCard.IMAGES_COUNT];
        for (int i = 0; i < DobbleCard.IMAGES_COUNT; i++) {
            final String name = new File(paths[i]).getName();
            images[i] = new DobbleImage(i + 1, "file:images/" + name);
        }

        return images;
    }

    private DobbleImage[] loadRandomCard() {
        final int rand = ThreadLocalRandom.current().nextInt(1, IMAGES_COUNT);
        return loadImagesForCard(rand);
    }

    // Shifts the cards to the right (the left card take place of the right one) and displays new left card.
    private void updateTableCard(final DobbleImage[] newLeftCard, boolean shift) {
        pane.getChildren().removeAll(leftCard, rightCard);
        leftCard.removeHighlight();
        rightCard.removeHighlight();

        if (shift)
            rightCard = leftCard;

        leftCard = new DobbleCard();
        leftCard.setImages(newLeftCard);

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

        scoreDisplay.textProperty().bind(Bindings.createStringBinding(() -> "Score: " + score.get(), score));
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        timeDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
    }

    public void endTheGame() {
        if (type == GameType.SINGLEPLAYER)
            scene.getRoot().fireEvent(new SingleplayerEndedEvent(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, "SaveResult.fxml", level, score.get()));
        else {
            connection.deletePlayer(clientId, clientId);
            if (isAdmin) {
                connection.endGameSession();
            }
            connection.getSession().disconnect();

            scene.getRoot().fireEvent(new SceneChangedEvent(SceneChangedEvent.SCENE_CHANGED_EVENT_TYPE, "MainMenu.fxml"));
        }
    }
}
