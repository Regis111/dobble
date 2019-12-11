package pl.dobblepolskab.gui;

import javafx.beans.binding.BooleanBinding;
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

    private DobbleCard leftCard;
    private DobbleCard rightCard;

    public DobbleWindow(Pane primaryPane, double widthProperty, double heightProperty, Paint paint) {
        super(primaryPane, widthProperty, heightProperty, paint);
        this.primaryPane = primaryPane;
        this.widthProperty = new SimpleDoubleProperty(widthProperty);
        this.heightProperty = new SimpleDoubleProperty(heightProperty);

        this.widthProperty.bind(widthProperty());
        this.heightProperty.bind(heightProperty());

        initialize();
    }

    private BooleanBinding isWidthSmaller;

    private void initialize() {
        Image[] images = new Image[8];
        images[0] = new Image("file:images/crayon.png");
        images[1] = new Image("file:images/heart.png");
        images[2] = new Image("file:images/heart.png");
        images[3] = new Image("file:images/crayon.png");
        images[4] = new Image("file:images/crayon.png");
        images[5] = new Image("file:images/crayon.png");
        images[6] = new Image("file:images/crayon.png");
        images[7] = new Image("file:images/crayon.png");

        final double gap = 10.0;
        final double cardRadius = (widthProperty.get() - gap * 4) / 4;
        final double cardCenterY = heightProperty.get() / 2;

        isWidthSmaller = widthProperty.lessThan(heightProperty);

        leftCard = new DobbleCard(images);
        leftCard.getRadiusProperty().bind(widthProperty.subtract(gap * 4).divide(4));
        leftCard.getCenterXProperty().bind(leftCard.getRadiusProperty().add(gap));
        leftCard.getCenterYProperty().bind(heightProperty.divide(2));
        leftCard.layoutImages();

        rightCard = new DobbleCard(images);
        rightCard.getRadiusProperty().bind(widthProperty.subtract(gap * 4).divide(4));
        rightCard.getCenterXProperty().bind(widthProperty.subtract(rightCard.getRadiusProperty()).subtract(gap));
        rightCard.getCenterYProperty().bind(heightProperty.divide(2));
        rightCard.layoutImages();

        primaryPane.getChildren().addAll(leftCard, rightCard);
    }
}
