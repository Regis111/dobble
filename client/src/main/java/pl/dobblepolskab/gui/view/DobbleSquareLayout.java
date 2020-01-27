package pl.dobblepolskab.gui.view;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import pl.dobblepolskab.gui.others.GameConstants;
import pl.dobblepolskab.gui.others.LayoutConstants;

public class DobbleSquareLayout implements DobbleCardLayout {
    private Rectangle[] rectangles = new Rectangle[GameConstants.DISPLAYED_IMAGES_COUNT];

    public DobbleSquareLayout(DobbleCard card) {
        for (int i = 0; i < GameConstants.DISPLAYED_IMAGES_COUNT; i++)
            rectangles[i] = new Rectangle();

        DoubleProperty centerX = card.getCenterXProperty();
        DoubleProperty centerY = card.getCenterYProperty();

        DoubleBinding squareShift = card.getRadiusProperty().divide(Math.sqrt(2));

        DoubleBinding xWithSquareShift = centerX.subtract(squareShift);
        DoubleBinding yWithSquareShift = centerY.subtract(squareShift);

        rectangles[0].xProperty().bind(xWithSquareShift);
        rectangles[0].yProperty().bind(yWithSquareShift);

        rectangles[1].xProperty().bind(centerX);
        rectangles[1].yProperty().bind(yWithSquareShift);

        rectangles[2].xProperty().bind(xWithSquareShift);
        rectangles[2].yProperty().bind(centerY);

        rectangles[3].xProperty().bind(centerX);
        rectangles[3].yProperty().bind(centerY);

        for (int i = 0; i < GameConstants.DISPLAYED_IMAGES_COUNT / 2; i++) {
            rectangles[i].heightProperty().bind(squareShift);
            rectangles[i].widthProperty().bind(squareShift);
        }

        double outerShiftAngle = Math.toRadians(22.5);
        DoubleBinding largeVerticalShift = card.getRadiusProperty().multiply(Math.sin(outerShiftAngle));
        DoubleBinding largeHorizontalShift = card.getRadiusProperty().multiply(Math.cos(outerShiftAngle));

        DoubleBinding smallHorizontalShift = largeHorizontalShift.subtract(squareShift);

        rectangles[4].xProperty().bind(centerX.subtract(largeVerticalShift));
        rectangles[4].yProperty().bind(centerY.subtract(largeHorizontalShift));
        rectangles[4].heightProperty().bind(smallHorizontalShift);
        rectangles[4].widthProperty().bind(largeVerticalShift.multiply(2));

        rectangles[5].xProperty().bind(centerX.subtract(largeHorizontalShift));
        rectangles[5].yProperty().bind(centerY.subtract(largeVerticalShift));
        rectangles[5].heightProperty().bind(largeVerticalShift.multiply(2));
        rectangles[5].widthProperty().bind(smallHorizontalShift);

        rectangles[6].xProperty().bind(centerX.subtract(largeVerticalShift));
        rectangles[6].yProperty().bind(centerY.add(squareShift));
        rectangles[6].heightProperty().bind(smallHorizontalShift);
        rectangles[6].widthProperty().bind(largeVerticalShift.multiply(2));

        rectangles[7].xProperty().bind(centerX.add(squareShift));
        rectangles[7].yProperty().bind(centerY.subtract(largeVerticalShift));
        rectangles[7].heightProperty().bind(largeVerticalShift.multiply(2));
        rectangles[7].widthProperty().bind(smallHorizontalShift);
    }

    @Override
    public void layoutImages(ImageView[] imageViews) {
        for (int i = 0; i < GameConstants.DISPLAYED_IMAGES_COUNT; i++)
            layoutImageInsideRectangle(rectangles[i], imageViews[i]);
    }

    /*
     * I know that this piece of code may be ugly but it is a private method
     * and enhances other parts of code.
     */
    private boolean isHeightLargerThanWidth(Node node) {
        if (node instanceof Rectangle)
            return ((Rectangle) node).getHeight() > ((Rectangle) node).getWidth();
        if (node instanceof ImageView)
            return ((ImageView) node).getFitHeight() > ((ImageView) node).getFitHeight();
        return false;
    }

    private boolean isImageRotationNeeded(Rectangle rectangle, ImageView image) {
        boolean borderShape = isHeightLargerThanWidth(rectangle);
        boolean imageShape = isHeightLargerThanWidth(image);

        return borderShape ^ imageShape;
    }

    /*
     * To work correctly, this method requires the image to be smaller than
     * border.
     */
    private void bindTopLeftCorner(Rectangle border, ImageView image) {
        DoubleBinding xShift = border.widthProperty().subtract(image.fitWidthProperty());
        xShift = xShift.divide(2);

        image.xProperty().bind(border.xProperty().add(xShift));

        DoubleBinding yShift = border.heightProperty().subtract(image.fitHeightProperty());
        yShift = yShift.divide(2);

        image.yProperty().bind(border.yProperty().add(yShift));
    }

    private void layoutImageInsideRectangle(Rectangle rectangle, ImageView imageView) {
        /*
         * Ensures that any scaling of the image does not disrupt the ratio of
         * its dimensions.
         */
        imageView.setPreserveRatio(true);

        // Bind the height and the width of the image
        DoubleBinding heightBinding = rectangle.heightProperty().multiply(LayoutConstants.RECTANGLE_SIZE_MULTIPLIER);
        DoubleBinding widthBinding = rectangle.widthProperty().multiply(LayoutConstants.RECTANGLE_SIZE_MULTIPLIER);

        imageView.fitHeightProperty().bind(heightBinding);
        imageView.fitWidthProperty().bind(widthBinding);

        bindTopLeftCorner(rectangle, imageView);

        imageView.setRotate(360 * Math.random());
    }
}