package gui;

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

    public DobbleCard(double centerX, double centerY, double radius, Image[] images) {
        setImages(images);
        circle = new Circle(centerX, centerY, radius);
        circle.setFill(Color.WHITE);
        ImageView view = new ImageView(images[0]);
        view.xProperty().bind(circle.centerXProperty());
        view.yProperty().bind(circle.centerYProperty());
        getChildren().addAll(circle, view);
    }

    private void setImages(Image[] images) {
        if (images.length != IMAGES_COUNT)
            throw new IllegalArgumentException("A card should contain " + IMAGES_COUNT + " images");
        this.images = images;
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
