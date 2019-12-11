package pl.dobblepolskab.common.gamecontent;

import java.util.HashMap;
import java.util.LinkedList;

public class GameContent {
    private boolean completed;
    private HashMap<Long, GameCard> gameCards;
    private HashMap<Integer, GameCardSymbol> gameCardSymbols;

    private void initObject() {
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
        initObject();
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

    public boolean containsCard(GameCard gameCard){
        return gameCards.containsKey(gameCard.getCardId());

    }

    public LinkedList<GameCard> getCards(){
        if(!completed)
            return new LinkedList<>();
        return new LinkedList<>(gameCards.values());
    }

}
