package pl.dobblepolskab.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

import static java.lang.Thread.sleep;

public class DobbleGameController {
    private static final double GAP = 10.0;

    @FXML
    private Scene scene;
    @FXML
    private DobbleCard leftCard;
    @FXML
    private DobbleCard rightCard;

    @FXML
    public void initialize() {
        ReadOnlyDoubleProperty width = scene.widthProperty();
        DoubleBinding height = scene.heightProperty().divide(2);
        DoubleBinding radius = width.subtract(GAP * 4).divide(4);

        leftCard.getRadiusProperty().bind(radius);
        leftCard.getCenterXProperty().bind(radius.add(GAP));
        leftCard.getCenterYProperty().bind(height);

        rightCard.getRadiusProperty().bind(radius);
        rightCard.getCenterXProperty().bind(width.subtract(radius).subtract(GAP));
        rightCard.getCenterYProperty().bind(height);

        EventHandler<MouseEvent> handler = mouseEvent -> {
            DobbleImage leftCardImage = leftCard.getHighlightedImage();
            DobbleImage rightCardImage = rightCard.getHighlightedImage();

            mouseEvent.consume();

            if (leftCardImage != null && rightCardImage != null) {
                String leftCardImagePath = leftCardImage.getPath();
                String rightCardImagePath = rightCardImage.getPath();
                if (leftCardImagePath.equals(rightCardImagePath)) {
                    //scene.getRoot().fireEvent(new SceneChangeEvent(SceneChangeEvent.SCENE_CHANGE_EVENT_TYPE, "DobbleMenu.fxml"));
                    setImages();
                }
            }
        };

        leftCard.getCardBase().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        rightCard.getCardBase().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);

        setImages();
    }

    private void setImages() {
        DobbleImage[] images = new DobbleImage[8];
        images[0] = new DobbleImage(1, "file:images/dolphin.png");
        images[1] = new DobbleImage(2, "file:images/heart.png");
        images[2] = new DobbleImage(3, "file:images/crayon.png");
        images[3] = new DobbleImage(4, "file:images/dolphin.png");
        images[4] = new DobbleImage(5, "file:images/heart.png");
        images[5] = new DobbleImage(6, "file:images/heart.png");
        images[6] = new DobbleImage(7, "file:images/crayon.png");
        images[7] = new DobbleImage(8, "file:images/crayon.png");

        leftCard.setImages(images);
        rightCard.setImages(images);
    }
}
