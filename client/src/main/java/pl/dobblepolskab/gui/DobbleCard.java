package pl.dobblepolskab.gui;

import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DobbleCard extends Group {
    private static final int IMAGES_COUNT = 8;

    private ImageView[] imageViews = new ImageView[IMAGES_COUNT];
    private DobbleImage highlightedImage;

    private Circle circle = new Circle(0, Color.WHITE);
    private DobbleCardLayout layout = new DobbleSquareLayout(this);

    public void setImages(DobbleImage[] images) {
        if (images == null || images.length != IMAGES_COUNT)
            throw new IllegalArgumentException("A card should contain " + IMAGES_COUNT + " images");

        highlightedImage = null;
        EventHandler<MouseEvent> handler = mouseEvent -> {
            ImageView source = (ImageView) mouseEvent.getSource();
            DobbleImage selectedImage = (DobbleImage) source.getImage();

            mouseEvent.consume();

            if (selectedImage != highlightedImage) {
                if (highlightedImage != null) {
                    int id = highlightedImage.getId() - 1;
                    images[id] = highlightedImage.getReversedImage();
                    imageViews[id].setImage(images[id]);
                }

                highlightedImage = selectedImage.getReversedImage();
                selectedImage = highlightedImage;
                source.setImage(selectedImage);

                circle.fireEvent(mouseEvent);
            }
        };

        for (int i = 0; i < IMAGES_COUNT; i++) {
            imageViews[i] = new ImageView(images[i]);
            imageViews[i].addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        }

        layout.layoutImages(imageViews);
        setRotate(360 * Math.random());

        getChildren().clear();
        getChildren().add(circle);
        getChildren().addAll(imageViews);
    }

    public DobbleImage getHighlightedImage() {
        return highlightedImage;
    }

    public Circle getCardBase() {
        return circle;
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
