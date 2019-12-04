package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class DobbleWindow extends Scene {
    private Pane primaryPane;

    private DoubleProperty widthProperty;
    private DoubleProperty heightProperty;

    public DobbleWindow(Pane primaryPane, double widthProperty, double heightProperty, Paint paint) {
        super(primaryPane, widthProperty, heightProperty, paint);
        this.primaryPane = primaryPane;
        this.widthProperty = new SimpleDoubleProperty(widthProperty);
        this.heightProperty = new SimpleDoubleProperty(heightProperty);

        this.widthProperty.bind(widthProperty());
        this.heightProperty.bind(heightProperty());

        initialize();
    }

    private void initialize() {
        Image[] images = new Image[8];
        images[0] = new Image("file:images/crayon.png");

        final double gap = 10.0;
        final double cardRadius = (widthProperty.get() - gap * 4) / 4;
        final double cardCenterY = heightProperty.get() / 2;

        DobbleCard leftCard = new DobbleCard(gap + cardRadius, cardCenterY, cardRadius, images);
        leftCard.getRadiusProperty().bind(widthProperty.subtract(gap * 4).divide(4));
        leftCard.getCenterXProperty().bind(leftCard.getRadiusProperty().add(gap));
        leftCard.getCenterYProperty().bind(heightProperty.divide(2));

        DobbleCard rightCard = new DobbleCard(gap * 3 + cardRadius * 3, cardCenterY, cardRadius, images);
        rightCard.getRadiusProperty().bind(widthProperty.subtract(gap * 4).divide(4));
        rightCard.getCenterXProperty().bind(rightCard.getRadiusProperty().multiply(3).add(gap * 3));
        rightCard.getCenterYProperty().bind(heightProperty.divide(2));

        primaryPane.getChildren().addAll(leftCard, rightCard);
    }
}
