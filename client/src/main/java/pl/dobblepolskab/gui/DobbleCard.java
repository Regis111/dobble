package pl.dobblepolskab.gui;

import javafx.beans.property.DoubleProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import javax.swing.event.ChangeEvent;

public class DobbleCard extends Group {
    public static final int IMAGES_COUNT = 8;

    private DobbleImage[] images;

    private DobbleImage highlightedImage;

    private Circle circle;

    private DobbleCardLayout layout;

    public DobbleCard(DobbleImage[] images) {
        circle = new Circle();
        setImages(images);
        circle.setFill(Color.WHITE);
    }

    public DobbleCard(double centerX, double centerY, double radius, DobbleImage[] images) {
        circle = new Circle(centerX, centerY, radius);
        setImages(images);
        circle.setFill(Color.WHITE);
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

    public DobbleImage[] getImages() {
        return images;
    }

    public void setImages(DobbleImage[] images) {
        if (images.length != IMAGES_COUNT)
            throw new IllegalArgumentException("A card should contain " + IMAGES_COUNT + " images");
        this.images = images;
    }

    public DobbleImage getHighlightedImage() {
        return highlightedImage;
    }

    public Circle getCardBase() {
        return circle;
    }

    public void refreshLayout() {
        if (layout == null)
            layout = new DobbleSquareLayout(this);

        ImageView[] imageViews = new ImageView[IMAGES_COUNT];
        for (int i = 0; i < IMAGES_COUNT; i++) {
            imageViews[i] = new ImageView(images[i]);
            imageViews[i].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    ImageView source = (ImageView) event.getSource();
                    DobbleImage dobbleImage = (DobbleImage) source.getImage();

                    if (highlightedImage != null) {
                        int id = highlightedImage.getId() - 1;
                        images[id] = highlightedImage.getReversedImage();
                        imageViews[id].setImage(images[id]);
                    }

                    highlightedImage = dobbleImage.getReversedImage();
                    dobbleImage = highlightedImage;
                    source.setImage(highlightedImage);

                    circle.fireEvent(event);

                    event.consume();
                }
            });
        }

        layout.layoutImages(imageViews);

        getChildren().clear();
        if (circle != null)
            getChildren().add(circle);
        getChildren().addAll(imageViews);

        setRotate(360 * Math.random());
    }
}
