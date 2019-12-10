package pl.dobblepolskab.common.gamecontent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class GameContentBox implements GameContentSource {
    private String directoryPath;
    private String[] imagesPaths;
    private int[] cardsDefs;
    private int nextSymbolId;
    private int nextCardId;
    private boolean gameContentCorupted;

    private void initClass() {
        directoryPath = "";
        imagesPaths = new String[50];
        cardsDefs = new int[55 * 8];
        nextSymbolId = 0;
        nextCardId = 0;
        gameContentCorupted = false;
    }

    public GameContentBox() {
        initClass();
    }

    public GameContentBox(String directoryPath) {
        initClass();
        bindToDirectory(directoryPath);
    }

    public boolean bindToDirectory(String directoryPath) {
        gameContentCorupted = true;
        String boxDefPath = directoryPath + File.separator + "gameContentBoxDef.txt";
        File boxDefFile = new File(boxDefPath);
        try (BufferedReader reader = new BufferedReader(new FileReader(boxDefFile))) {
            String fileLine;
            while ((fileLine = reader.readLine()) != null) {
                if (fileLine.matches("(.*)S: (.*)")) {
                    String[] def = fileLine.split("S: ");
                    int symbolId = Integer.parseInt(def[0]) - 1;
                    if(symbolId < 0 || symbolId > 49)
                        return false;
                    imagesPaths[symbolId] = def[1];
                }
                if (fileLine.matches("(.*)C: (.*)")) {
                    String[] def = fileLine.split("C: ");
                    int cardId = Integer.parseInt(def[0]) - 1;
                    if(cardId < 0  || cardId > 54)
                        return false;
                    String[] cardSymbolsStr = def[1].split(", ");
                    int symbolId = 0;
                    for (String cardSymbolStr : cardSymbolsStr) {
                        cardsDefs[cardId * 8 + symbolId++] = Integer.parseInt(cardSymbolStr);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File " + boxDefPath + " not found, so directory doesn't contains game content.");
            return false;
        }
        for (String tempPath : imagesPaths) {
            if (tempPath == null || tempPath.equals(""))
                return false;
        }
        for (int tempValue : cardsDefs) {
            if (tempValue < 1 || tempValue > 50)
                return false;
        }
        gameContentCorupted = false;
        this.directoryPath = directoryPath;
        nextSymbolId = 0;
        nextCardId = 0;
        return true;
    }

    @Override
    public GameCardSymbol getNextGameCardSymbol() {
        if (gameContentCorupted)
            return null;
        File imageFile = new File(imagesPaths[nextSymbolId]);
        try {
            BufferedImage image = ImageIO.read(imageFile);
            nextSymbolId++;
            return new GameCardSymbol(image);
        } catch (IOException e) {
            gameContentCorupted = true;
            return null;
        }
    }

    @Override
    public GameCard getNextGameCard() {
        if (gameContentCorupted)
            return null;
        int [] cardValues = Arrays.copyOfRange(cardsDefs, nextCardId* 8, (nextCardId+1)*8);
        nextCardId++;
        return new GameCard(cardValues);
    }
}
