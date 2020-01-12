package gamecontent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class GameContentBox implements GameContentSource {
    private String directoryPath;
    private final String[] imagesPaths;
    private final int[] cardsDefs;
    private int nextSymbolId;
    private int nextCardId;
    private boolean gameContentCorupted;

    public GameContentBox() {
        initObject();
        imagesPaths = new String[GameContent.finalGameCardSymbolsNumber];
        cardsDefs = new int[GameContent.finalGameCardsNumber * GameCard.finalSymbolsNumber];
    }

    public GameContentBox(String directoryPath) {
        initObject();
        imagesPaths = new String[GameContent.finalGameCardSymbolsNumber];
        cardsDefs = new int[GameContent.finalGameCardsNumber * GameCard.finalSymbolsNumber];
        bindToDirectory(directoryPath);
    }

    private void initObject() {
        directoryPath = "";
        nextSymbolId = 0;
        nextCardId = 0;
        gameContentCorupted = false;
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
                    if(symbolId < 0 || symbolId > GameContent.finalGameCardSymbolsNumber - 1)
                        return false;
                    imagesPaths[symbolId] = def[1];
                }
                if (fileLine.matches("(.*)C: (.*)")) {
                    String[] def = fileLine.split("C: ");
                    int cardId = Integer.parseInt(def[0]) - 1;
                    if(cardId < 0  || cardId > GameContent.finalGameCardsNumber - 1)
                        return false;
                    String[] cardSymbolsStr = def[1].split(", ");
                    int symbolId = 0;
                    for (String cardSymbolStr : cardSymbolsStr) {
                        cardsDefs[cardId * GameCard.finalSymbolsNumber + symbolId++] = Integer.parseInt(cardSymbolStr);
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
            if (tempValue < 1 || tempValue > GameContent.finalGameCardSymbolsNumber)
                return false;
        }
        gameContentCorupted = false;
        this.directoryPath = directoryPath;
        nextSymbolId = 0;
        nextCardId = 0;
        return true;
    }

    @Override
    public Optional<GameCardSymbol> getNextGameCardSymbol() {
        if (gameContentCorupted || nextSymbolId == GameContent.finalGameCardSymbolsNumber)
            return Optional.empty();
        String path = directoryPath + File.separator + imagesPaths[nextSymbolId];
        nextSymbolId++;
        return Optional.of(new GameCardSymbol(path));
    }

    @Override
    public Optional<GameCard> getNextGameCard() {
        if (gameContentCorupted || nextCardId == GameContent.finalGameCardsNumber)
            return Optional.empty();
        int [] cardValues = Arrays.copyOfRange(cardsDefs, nextCardId* GameCard.finalSymbolsNumber,
                (nextCardId+1)*GameCard.finalSymbolsNumber);
        nextCardId++;
        return Optional.of(new GameCard(cardValues));
    }
}
