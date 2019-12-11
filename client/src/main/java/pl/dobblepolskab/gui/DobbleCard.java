package pl.dobblepolskab.gui;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DobbleCard extends Group {
    private static final int IMAGES_COUNT = 8;

    private Image[] images = new Image[IMAGES_COUNT];
    private Circle circle;

    private DobbleCardLayout layout = new DobbleSquareLayout();

    public DobbleCard(Image[] images) {
        setImages(images);
        circle = new Circle();
        initialize();
    }

    public DobbleCard(double centerX, double centerY, double radius, Image[] images) {
        setImages(images);
        circle = new Circle(centerX, centerY, radius);
        circle.setFill(Color.WHITE);
        initialize();
    }

    private void initialize() {
        circle.setFill(Color.WHITE);
        //ImageView view = new ImageView(images[0]);
        //view.xProperty().bind(circle.centerXProperty());
        //view.yProperty().bind(circle.centerYProperty());

        //layout.layoutImages(this, new ImageView[8]);
        getChildren().addAll(circle);
        circle.toBack();
    }

    private void setImages(Image[] images) {
        if (images.length != IMAGES_COUNT)
            throw new IllegalArgumentException("A card should contain " + IMAGES_COUNT + " images");
        this.images = images;
    }

    public void layoutImages() {
        ImageView[] imageViews = new ImageView[8];
        for (int i = 0; i < 8; i++)
            imageViews[i] = new ImageView(images[i]);
        layout.layoutImages(this, imageViews);
        getChildren().addAll(imageViews);

    }

    public DoubleProperty getRadiusProperty() {
        return circle.radiusProperty();
    }

    public DoubleProperty getCenterXProperty() {
        return circle.centerXProperty();
    }

    public DoubleProperty getCenterYProperty() {
        return circle.centerYProperty();
    }
}
