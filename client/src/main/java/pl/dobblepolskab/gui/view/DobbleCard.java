package pl.dobblepolskab.gui.view;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import pl.dobblepolskab.gui.others.GameConstants;

public class DobbleCard extends Group {

    private DobbleImage[] images;
    private ImageView[] imageViews = new ImageView[GameConstants.DISPLAYED_IMAGES_COUNT];
    private DobbleImage highlightedImage;

    private Circle circle = new Circle(0, Color.WHITE);
    private DobbleCardLayout layout = new DobbleSquareLayout(this);

    public void setImages(DobbleImage[] images) {
        if (images == null || images.length != GameConstants.DISPLAYED_IMAGES_COUNT)
            throw new IllegalArgumentException("A card should contain " + GameConstants.DISPLAYED_IMAGES_COUNT + " images");
        this.images = images;

        highlightedImage = null;
        EventHandler<MouseEvent> handler = mouseEvent -> {
            ImageView source = (ImageView) mouseEvent.getSource();
            DobbleImage selectedImage = (DobbleImage) source.getImage();

            if (selectedImage != highlightedImage) {
                removeHighlight();

                highlightedImage = selectedImage.getReversedImage();
                selectedImage = highlightedImage;
                source.setImage(selectedImage);
            }
        };

        for (int i = 0; i < GameConstants.DISPLAYED_IMAGES_COUNT; i++) {
            imageViews[i] = new ImageView(images[i]);
            imageViews[i].addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
        }

        layout.layoutImages(imageViews);
        setRotate(360 * Math.random());

        Platform.runLater(() -> {
            getChildren().clear();
            getChildren().add(circle);
            getChildren().addAll(imageViews);
        });
    }

    public DobbleImage getHighlightedImage() {
        return highlightedImage;
    }

    public void removeHighlight() {
        if (highlightedImage != null) {
            int id = highlightedImage.getId() - 1;
            images[id] = highlightedImage.getReversedImage();
            imageViews[id].setImage(images[id]);
            highlightedImage = null;
        }
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
