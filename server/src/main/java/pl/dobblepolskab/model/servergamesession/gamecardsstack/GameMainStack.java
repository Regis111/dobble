package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;

import java.util.LinkedList;

public class GameMainStack extends GameCardsStack {
    private boolean initiated;

    private void initObject() {
        initiated = false;
    }

    public GameMainStack(GameContent gameContent) {
        setUpGameContent(gameContent);
    }

    public GameMainStack(GameContent gameContent, LinkedList<GameCard> cardsToAdd) {
        super();
        setUpGameContent(gameContent);
        initMainStack(cardsToAdd);
    }

    public boolean initMainStack(LinkedList<GameCard> cardsToAdd) {
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
