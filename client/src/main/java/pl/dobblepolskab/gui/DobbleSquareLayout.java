package pl.dobblepolskab.gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DobbleSquareLayout implements DobbleCardLayout {
    private Rectangle[] rectangles;// = new Rectangle[8];
    private DoubleProperty squareShift;
    private DoubleProperty rectangleLargeVerticalShift;
    private DoubleProperty rectangleLargeHorizontalShift;
    private DoubleProperty rectangleSmallVerticalShift;
    private DoubleProperty rectangleSmallHorizontalShift;
    private DobbleCard dobbleCard;
    private DoubleProperty heightDifference;


    public DobbleSquareLayout() {
        rectangles = new Rectangle[8];
        squareShift = new SimpleDoubleProperty();
        rectangleLargeVerticalShift = new SimpleDoubleProperty();
        rectangleLargeHorizontalShift = new SimpleDoubleProperty();
        rectangleSmallVerticalShift = new SimpleDoubleProperty();
        rectangleSmallHorizontalShift = new SimpleDoubleProperty();
    }

    @Override
    public void layoutImages(DobbleCard card, ImageView[] imageViews) {
        dobbleCard = card;
        squareShift.bind(dobbleCard.getRadiusProperty().divide(Math.sqrt(2.0)));
        rectangleLargeVerticalShift.bind(dobbleCard.getRadiusProperty().multiply(Math.sin(22.5 / 180.0 * Math.PI)));
        rectangleLargeHorizontalShift.bind(dobbleCard.getRadiusProperty().multiply(Math.cos(22.5 / 180.0 * Math.PI)));
        rectangleSmallVerticalShift.bind(rectangleLargeVerticalShift.subtract(squareShift));
        rectangleSmallHorizontalShift.bind(rectangleLargeHorizontalShift.subtract(squareShift));

        rectangles[0] = new Rectangle();
        //rectangles[0].setStroke(Color.BLACK);
        rectangles[0].setFill(Color.TRANSPARENT);
        rectangles[0].xProperty().bind(dobbleCard.getCenterXProperty().subtract(squareShift));
        rectangles[0].yProperty().bind(dobbleCard.getCenterYProperty().subtract(squareShift));
        rectangles[0].heightProperty().bind(squareShift);
        rectangles[0].widthProperty().bind(rectangles[0].heightProperty());

        rectangles[1] = new Rectangle();
        //rectangles[1].setStroke(Color.BLUE);
        rectangles[1].setFill(Color.TRANSPARENT);
        rectangles[1].xProperty().bind(dobbleCard.getCenterXProperty());
        rectangles[1].yProperty().bind(dobbleCard.getCenterYProperty().subtract(squareShift));
        rectangles[1].heightProperty().bind(squareShift);
        rectangles[1].widthProperty().bind(rectangles[1].heightProperty());

        rectangles[2] = new Rectangle();
        //rectangles[2].setStroke(Color.RED);
        rectangles[2].setFill(Color.TRANSPARENT);
        rectangles[2].xProperty().bind(dobbleCard.getCenterXProperty().subtract(squareShift));
        rectangles[2].yProperty().bind(dobbleCard.getCenterYProperty());
        rectangles[2].heightProperty().bind(squareShift);
        rectangles[2].widthProperty().bind(rectangles[2].heightProperty());

        rectangles[3] = new Rectangle();
        //rectangles[3].setStroke(Color.VIOLET);
        rectangles[3].setFill(Color.TRANSPARENT);
        rectangles[3].xProperty().bind(dobbleCard.getCenterXProperty());
        rectangles[3].yProperty().bind(dobbleCard.getCenterYProperty());
        rectangles[3].heightProperty().bind(squareShift);
        rectangles[3].widthProperty().bind(rectangles[3].heightProperty());

        rectangles[4] = new Rectangle();
        //rectangles[4].setStroke(Color.PURPLE);
        rectangles[4].setFill(Color.TRANSPARENT);
        rectangles[4].xProperty().bind(dobbleCard.getCenterXProperty().subtract(rectangleLargeVerticalShift));
        rectangles[4].yProperty().bind(dobbleCard.getCenterYProperty().subtract(rectangleLargeHorizontalShift));
        rectangles[4].heightProperty().bind(rectangleLargeHorizontalShift.subtract(squareShift));
        rectangles[4].widthProperty().bind(rectangleLargeVerticalShift.multiply(2));

        rectangles[5] = new Rectangle();
        //rectangles[5].setStroke(Color.MAGENTA);
        rectangles[5].setFill(Color.TRANSPARENT);
        rectangles[5].xProperty().bind(dobbleCard.getCenterXProperty().subtract(rectangleLargeHorizontalShift));
        rectangles[5].yProperty().bind(dobbleCard.getCenterYProperty().subtract(rectangleLargeVerticalShift));
        rectangles[5].heightProperty().bind(rectangleLargeVerticalShift.multiply(2));
        rectangles[5].widthProperty().bind(rectangleLargeHorizontalShift.subtract(squareShift));

        rectangles[6] = new Rectangle();
        //rectangles[6].setStroke(Color.ORANGE);
        rectangles[6].setFill(Color.TRANSPARENT);
        rectangles[6].xProperty().bind(dobbleCard.getCenterXProperty().subtract(rectangleLargeVerticalShift));
        rectangles[6].yProperty().bind(dobbleCard.getCenterYProperty().add(squareShift));
        rectangles[6].heightProperty().bind(rectangleLargeHorizontalShift.subtract(squareShift));
        rectangles[6].widthProperty().bind(rectangleLargeVerticalShift.multiply(2));

        rectangles[7] = new Rectangle();
        //rectangles[7].setStroke(Color.GRAY);
        rectangles[7].setFill(Color.TRANSPARENT);
        rectangles[7].xProperty().bind(dobbleCard.getCenterXProperty().add(squareShift));
        rectangles[7].yProperty().bind(dobbleCard.getCenterYProperty().subtract(rectangleLargeVerticalShift));
        rectangles[7].heightProperty().bind(rectangleLargeVerticalShift.multiply(2));
        rectangles[7].widthProperty().bind(rectangleLargeHorizontalShift.subtract(squareShift));

        for (int i = 0; i < 4; i++)
            layoutSquareImage(rectangles[i], imageViews[i]);

        for (int i = 4; i < 8; i++)
            layoutRectangeImage(rectangles[i], imageViews[i]);

        card.getChildren().addAll(rectangles);
    }

    private void layoutSquareImage(Rectangle square, ImageView imageView) {
        imageView.setPreserveRatio(true);
        if (imageView.getFitHeight() > imageView.getFitWidth())
            if (square.getHeight() > square.getWidth())
                imageView.fitHeightProperty().bind(square.heightProperty().multiply(0.8));
            else
                imageView.fitHeightProperty().bind(square.widthProperty().multiply(0.8));
        else
        if (square.getHeight() > square.getWidth())
            imageView.fitWidthProperty().bind(square.heightProperty().multiply(0.8));
        else
            imageView.fitWidthProperty().bind(square.widthProperty().multiply(0.8));

        if (imageView.getFitWidth() == 0)
            imageView.xProperty().bind(square.xProperty().add(squareShift.subtract(imageView.getImage().widthProperty()).divide(2)));
        else
            imageView.xProperty().bind(square.xProperty().add(squareShift.subtract(imageView.fitWidthProperty()).divide(2)));

        if (imageView.getFitHeight() == 0)
            imageView.yProperty().bind(square.yProperty().add(squareShift.subtract(imageView.getImage().heightProperty()).divide(2)));
        else
            imageView.yProperty().bind(square.yProperty().add(squareShift.subtract(imageView.fitHeightProperty()).divide(2)));

        imageView.setRotate(360 * Math.random());
    }

    private void layoutRectangeImage(Rectangle rectangle, ImageView imageView) {
        imageView.setPreserveRatio(true);
        boolean rotated = false;
        if (imageView.getFitHeight() > imageView.getFitWidth())
            if (rectangle.getHeight() > rectangle.getWidth())
                imageView.fitHeightProperty().bind(rectangle.heightProperty().multiply(0.8));
            else {
                imageView.fitHeightProperty().bind(rectangle.widthProperty().multiply(0.8));
                imageView.setRotate(90);
                rotated = true;
            }
        else
            if (rectangle.getHeight() > rectangle.getWidth()) {
                imageView.fitWidthProperty().bind(rectangle.heightProperty().multiply(0.8));
                imageView.setRotate(90);
                rotated = true;
            }
            else
                imageView.fitWidthProperty().bind(rectangle.widthProperty().multiply(0.8));

        if (rotated)
            imageView.xProperty().bind(rectangle.xProperty().subtract(rectangle.widthProperty()));
        else
            imageView.xProperty().bind(rectangle.xProperty());

        if (imageView.getFitHeight() == 0)
            imageView.yProperty().bind(rectangle.yProperty().add(rectangle.heightProperty().subtract(imageView.getImage().heightProperty()).divide(2)));
        else
            imageView.yProperty().bind(rectangle.yProperty().add(rectangle.heightProperty().subtract(imageView.fitHeightProperty()).divide(2)));
        //imageView.yProperty().bind(rectangle.yProperty().add(rectangle.heightProperty().divide(2)));

        imageView.setRotate(imageView.getRotate() + 180 * Math.round(Math.random()));

    }
}