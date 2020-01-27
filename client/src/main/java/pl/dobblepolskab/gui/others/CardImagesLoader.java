package pl.dobblepolskab.gui.others;

import gamecontent.GameContent;
import pl.dobblepolskab.gui.view.DobbleImage;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

public class CardImagesLoader {

    private static CardImagesLoader instance = null;
    private static final String IMAGES_PATH = "file:images/";

    private CardImagesLoader() {

    }

    public static CardImagesLoader getInstance() {
        if (instance == null)
            instance = new CardImagesLoader();
        return instance;
    }

    public DobbleImage[] loadImagesForCard(int cardId) {
        final GameContent gameContent = GameContent.getInstance();

        String[] paths = gameContent.getGameCardSymbolPaths(cardId);
        DobbleImage[] images = new DobbleImage[GameConstants.DISPLAYED_IMAGES_COUNT];
        for (int i = 0; i < GameConstants.DISPLAYED_IMAGES_COUNT; i++) {
            final String name = new File(paths[i]).getName();
            images[i] = new DobbleImage(i + 1, IMAGES_PATH + name);
        }

        return images;
    }

    public DobbleImage[] loadRandomCard() {
        final int rand = ThreadLocalRandom.current().nextInt(1, GameConstants.IMAGES_COUNT);
        return loadImagesForCard(rand);
    }
}
