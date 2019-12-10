package pl.dobblepolskab.common.gamecontent;

import java.util.HashMap;

public class GameContent {
    private boolean completed;
    private HashMap<Long, GameCard> gameCards;
    private HashMap<Integer, GameCardSymbol> gameCardSymbols;

    private void initClass() {
        completed = false;
        gameCards = new HashMap<>();
        gameCardSymbols = new HashMap<>();
    }

    private boolean checkCompletion() {
        if (gameCards.size() < 55 || gameCardSymbols.size() < 50)
            return false;
        completed = true;
        return true;
    }

    public GameContent() {
        initClass();
    }

    public boolean addGameCard(GameCard gameCard) {
        if (gameCards.size() >= 55)
            return false;
        if (gameCards.containsKey(gameCard.getCardId()))
            return false;
        gameCards.put(gameCard.getCardId(), gameCard);
        checkCompletion();
        return true;
    }

    public boolean addGameCardSymbol(GameCardSymbol gameCardSymbol) {
        if (gameCardSymbols.size() >= 50)
            return false;
        if (gameCardSymbols.containsKey(gameCardSymbol.getSymbolId()))
            return false;
        gameCardSymbols.put(gameCardSymbol.getSymbolId(), gameCardSymbol);
        checkCompletion();
        return true;
    }

    public boolean importGameContent(GameContentSource gameContentSource) {
        while (addGameCardSymbol(gameContentSource.getNextGameCardSymbol())) ;
        while (addGameCard(gameContentSource.getNextGameCard())) ;
        return completed;
    }

}
