package pl.dobblepolskab.common.gamecontent;

import java.util.*;

public class GameContent {
    private boolean completed;
    private HashMap<Long, GameCard> gameCards;
    private HashMap<Integer, GameCardSymbol> gameCardSymbols;
    public static final int finalGameCardSymbolsNumber = 50;
    public static final int finalGameCardsNumber = 55;

    public GameContent() {
        initObject();
    }

    private void initObject() {
        completed = false;
        gameCards = new HashMap<>();
        gameCardSymbols = new HashMap<>();
    }

    private void checkCompletion() {
        completed = !(gameCards.size() < finalGameCardsNumber || gameCardSymbols.size() < finalGameCardSymbolsNumber);
    }

    public boolean addGameCard(GameCard gameCard) {
        if (gameCards.size() >= finalGameCardsNumber)
            return false;
        if (gameCards.containsKey(gameCard.getCardId()))
            return false;
        gameCards.put(gameCard.getCardId(), gameCard);
        checkCompletion();
        return true;
    }

    public boolean addGameCardSymbol(GameCardSymbol gameCardSymbol) {
        if (gameCardSymbols.size() >= finalGameCardSymbolsNumber)
            return false;
        if (gameCardSymbols.containsKey(gameCardSymbol.getSymbolId()))
            return false;
        gameCardSymbols.put(gameCardSymbol.getSymbolId(), gameCardSymbol);
        checkCompletion();
        return true;
    }

    public boolean importGameContent(GameContentSource gameContentSource) {
        Optional<GameCardSymbol> gameCardSymbolOptional;
        do {
            gameCardSymbolOptional = gameContentSource.getNextGameCardSymbol();
            if(!gameCardSymbolOptional.isPresent())
                break;
            addGameCardSymbol(gameCardSymbolOptional.get());
        }
        while (true);
        Optional<GameCard> gameCardOptional;
        do {
            gameCardOptional = gameContentSource.getNextGameCard();
            if(!gameCardOptional.isPresent())
                break;
            addGameCard(gameCardOptional.get());
        }
        while (true);
        return completed;
    }

    public boolean containsCard(GameCard gameCard){
        return gameCards.containsKey(gameCard.getCardId());

    }

    public List<GameCard> getCards(){
        return ((!completed) ? (Collections.emptyList()) : new ArrayList<>(gameCards.values()));
    }

}
