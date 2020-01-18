package pl.dobblepolskab.gui.controllers;

import gamecontent.DifficultyLevel;
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
import messages.responses.AmIWinnerResponse;
import messages.responses.ResponseType;
import pl.dobblepolskab.gui.events.*;
import pl.dobblepolskab.gui.others.CardImagesLoader;
import pl.dobblepolskab.gui.view.DobbleCard;
import pl.dobblepolskab.gui.view.DobbleImage;
import pl.dobblepolskab.model.GameType;
import pl.dobblepolskab.gui.others.LayoutConstants;
import pl.dobblepolskab.gui.others.Timer;
import websocket.ServerSDK;

public class GameTableController {

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
    private final CardImagesLoader loader = CardImagesLoader.getInstance();

    // This field is used only in the singleplayer mode.
    private DifficultyLevel level;
    private int duration;

    // This fields are used only in the multiplayer mode.
    private ServerSDK connection;
    private String clientId;
    private int[] initialCards;
    private int turnId;

    public GameTableController(DifficultyLevel level) {
        this.type = GameType.SINGLEPLAYER;
        this.level = level;
        this.score = new SimpleIntegerProperty(0);
        this.duration = level.getGameTimeInSeconds();
    }

    public GameTableController(ServerSDK connection, int[] cards, int gameTimeInMinutes) {
        this.type = GameType.MULTIPLAYER;
        this.connection = connection;
        this.clientId = connection.getStompSessionHandler().getClientID();
        this.initialCards = cards;
        this.score = new SimpleIntegerProperty(0);
        this.turnId = 1;
        this.duration = gameTimeInMinutes * 60; // Conversion to seconds
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
        rightCard.setImages(loader.loadRandomCard());
        updateTableCard(loader.loadRandomCard(), false);

        scene.getRoot().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            e.consume();

            if (isCorrectPair()) {
                score.set(score.get() + 1);
                updateTableCard(loader.loadRandomCard(), true);
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
            Platform.runLater(() -> updateTableCard(loader.loadImagesForCard(newCardId), shift));
        });

        rightCard.setImages(loader.loadImagesForCard(initialCards[1]));
        updateTableCard(loader.loadImagesForCard(initialCards[0]), false);

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

    // Shifts the cards to the right (the left card take place of the right one) and displays new left card.
    private void updateTableCard(DobbleImage[] newLeftCard, boolean shift) {
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
        DoubleBinding cardRadius = scene.widthProperty().subtract(LayoutConstants.ITEMS_GAP * 4).divide(4);

        leftCard.getRadiusProperty().bind(cardRadius);
        leftCard.getCenterXProperty().bind(cardRadius.add(LayoutConstants.ITEMS_GAP));
        leftCard.getCenterYProperty().bind(cardHeight);

        rightCard.getRadiusProperty().bind(cardRadius);
        rightCard.getCenterXProperty().bind(width.subtract(cardRadius).subtract(LayoutConstants.ITEMS_GAP));
        rightCard.getCenterYProperty().bind(cardHeight);

        DoubleBinding fontSize = width.add(height).divide(LayoutConstants.FONT_SIZE_MULTIPLIER);

        scoreDisplay.textProperty().bind(Bindings.createStringBinding(() -> "Score: " + score.get(), score));
        scoreDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));

        timeDisplay.styleProperty().bind(Bindings.concat("-fx-font-size: ", fontSize.asString()));
    }

    public void endTheGame() {
        if (type == GameType.SINGLEPLAYER)
            scene.getRoot().fireEvent(new SingleplayerEndedEvent(SingleplayerEndedEvent.SINGLEPLAYER_ENDED_EVENT_TYPE, level, score.get()));
        else
            scene.getRoot().fireEvent(new MultiplayerEndedEvent(MultiplayerEndedEvent.MULTIPLAYER_ENDED_EVENT_TYPE));
    }
}
