package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;

import java.util.LinkedList;
import java.util.List;

public class GameMainStack extends GameCardsStack {
    private boolean initiated;

    public GameMainStack(GameContent gameContent) {
        super(gameContent);
        initObject();
    }

    public GameMainStack(GameContent gameContent, List<GameCard> cardsToAdd) {
        super(gameContent);
        initObject();
        initMainStack(cardsToAdd);
    }

    private void initObject() {
        initiated = false;
    }

    public boolean initMainStack(List<GameCard> cardsToAdd) {
        if(initiated)
            return false;
        for (GameCard curCard : cardsToAdd)
            if (!push(curCard))
                return false;
        initiated = true;
        return true;
    }

    @Override
    public boolean pushCard(GameCard card) {
        return false;
    }

    @Override
    public GameCard popCard() {
        return pop();
    }

    public boolean isAllCardsPopped(){
        return (getCardsCount() == 0);
    }

    public int getCardsCount(){
        return getStackSize();
    }

    public GameCard getTopCard(){
        return getCardOnTop();
    }
}
