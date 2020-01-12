package gamecontent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GameContent {
    static GameContent instance = null;
    private boolean completed;
    private ArrayList<GameCard> gameCards;
    private ArrayList<GameCardSymbol> gameCardSymbols;
    public static final int finalGameCardSymbolsNumber = 57;
    public static final int finalGameCardsNumber = 57;

    public static GameContent getInstance(){
        if(instance == null) {
            instance = new GameContent();
            GameContentBox contentBox = new GameContentBox("server/build/resources/main/dobbleDefaultContentBox");
            if(!instance.importGameContent(contentBox))
                throw new RuntimeException("Cannot import game content from default game content box!");
        }
        return instance;
    }

    private GameContent() {
        initObject();
    }

    private void initObject() {
        completed = false;
        gameCards = new ArrayList<>();
        gameCardSymbols = new ArrayList<>();
    }

    private void checkCompletion() {
        completed = !(gameCards.size() < finalGameCardsNumber || gameCardSymbols.size() < finalGameCardSymbolsNumber);
    }

    public boolean addGameCard(GameCard gameCard) {
        if (gameCards.size() >= finalGameCardsNumber)
            return false;
        if (gameCards.contains(gameCard))
            return false;
        gameCards.add(gameCard);
        checkCompletion();
        return true;
    }

    public boolean addGameCardSymbol(GameCardSymbol gameCardSymbol) {
        if (gameCardSymbols.size() >= finalGameCardSymbolsNumber)
            return false;
        if (gameCardSymbols.contains(gameCardSymbol))
            return false;
        gameCardSymbols.add(gameCardSymbol);
        checkCompletion();
        return true;
    }

    public boolean importGameContent(GameContentSource gameContentSource) {
        Optional<GameCardSymbol> gameCardSymbolOptional;
        do {
            gameCardSymbolOptional = gameContentSource.getNextGameCardSymbol();
            if (!gameCardSymbolOptional.isPresent())
                break;
            addGameCardSymbol(gameCardSymbolOptional.get());
        }
        while (true);
        Optional<GameCard> gameCardOptional;
        do {
            gameCardOptional = gameContentSource.getNextGameCard();
            if (!gameCardOptional.isPresent())
                break;
            addGameCard(gameCardOptional.get());
        }
        while (true);
        return completed;
    }

    public boolean containsCard(GameCard gameCard) {
        ArrayList<GameCard> list = new ArrayList<>();
        return gameCards.contains(gameCard);

    }

    public int getCardIdInModel(GameCard gameCard) {
        return gameCards.indexOf(gameCard);
    }

    public List<GameCard> getCards() {
        return ((!completed) ? (Collections.emptyList()) : new ArrayList<>(gameCards));
    }

    public String[] getGameCardSymbolPaths(int gameContentCardId) {
        String[] pathsArray = new String[GameCard.finalSymbolsNumber];
        if (gameContentCardId < 1 || gameContentCardId > finalGameCardsNumber || !completed) {
            for (int i = 0; i < GameCard.finalSymbolsNumber; i++) {
                pathsArray[i] = "";
            }
        } else {
            int[] cardSymbols = gameCards.get(gameContentCardId-1).getValues();
            for (int i = 0; i < GameCard.finalSymbolsNumber; i++) {
                pathsArray[i] = gameCardSymbols.get(cardSymbols[i]-1).getImagePath();
            }
        }
        return pathsArray;
    }
}
