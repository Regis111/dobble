package pl.dobblepolskab.gui;

import javafx.scene.image.Image;

/*
 * This class may seem pointless... and it is to some extend. It was created
 * because the JavaFX API used in the project does not contain the getUrl
 * method needed to, for example, reverse the image.
 */
public class DobbleImage extends Image {
    private int id;
    private String path;

    public DobbleImage(int id, String path) {
        super(path);
        this.id = id;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public DobbleImage getReversedImage() {
        String newPath;
        if (path.endsWith("2.png"))
            newPath = path.replace("2.png", ".png");
        else
            newPath = path.replace(".png", "2.png");

        return new DobbleImage(id, newPath);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
