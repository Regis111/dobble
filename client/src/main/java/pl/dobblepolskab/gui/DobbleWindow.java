package pl.dobblepolskab.gui;

import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class DobbleWindow extends Scene {
    private Pane primaryPane;

    private DobbleCard leftCard;
    private DobbleCard rightCard;

    public DobbleWindow(Pane primaryPane, double widthProperty, double heightProperty, Paint paint) {
        super(primaryPane, widthProperty, heightProperty, paint);
        this.primaryPane = primaryPane;

        initialize();
    }

    private void initialize() {
        DobbleImage[] images = new DobbleImage[8];
        images[0] = new DobbleImage(1, "file:images/dolphin.png");
        images[1] = new DobbleImage(2, "file:images/heart.png");
        images[2] = new DobbleImage(3, "file:images/crayon.png");
        images[3] = new DobbleImage(4, "file:images/dolphin.png");
        images[4] = new DobbleImage(5, "file:images/heart.png");
        images[5] = new DobbleImage(6, "file:images/heart.png");
        images[6] = new DobbleImage(7, "file:images/crayon.png");
        images[7] = new DobbleImage(8, "file:images/crayon.png");

        final double gap = 10.0;
        DoubleBinding width = widthProperty().subtract(gap * 4).divide(4);
        DoubleBinding height = heightProperty().divide(2);

        leftCard = new DobbleCard(images);
        leftCard.getRadiusProperty().bind(width);
        leftCard.getCenterXProperty().bind(leftCard.getRadiusProperty().add(gap));
        leftCard.getCenterYProperty().bind(height);
        leftCard.refreshLayout();

        rightCard = new DobbleCard(images);
        rightCard.getRadiusProperty().bind(width);
        rightCard.getCenterXProperty().bind(widthProperty().subtract(rightCard.getRadiusProperty()).subtract(gap));
        rightCard.getCenterYProperty().bind(height);
        rightCard.refreshLayout();

        EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DobbleImage leftCardImage = leftCard.getHighlightedImage();
                DobbleImage rightCardImage = rightCard.getHighlightedImage();

                if (leftCardImage != null && rightCardImage != null) {
                    String leftCardImagePath = leftCardImage.getPath();
                    String rightCardImagePath = rightCardImage.getPath();
                    if (leftCardImagePath.equals(rightCardImagePath))
                        System.out.println("NOICE");
                }
            }
        };

        leftCard.getCardBase().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        rightCard.getCardBase().addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        primaryPane.getChildren().addAll(leftCard, rightCard);
    }
}
