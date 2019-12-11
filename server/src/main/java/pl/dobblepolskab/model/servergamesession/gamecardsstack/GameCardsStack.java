package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;

import java.util.LinkedList;

public abstract class GameCardsStack {
    private static GameContent gameContent;
    private LinkedList<GameCard> cardsOnStack;

    static {
        gameContent = null;
    }

    private void initObject() {
        cardsOnStack = new LinkedList<>();
    }

    private boolean isNotSetUpped(){
        return (gameContent == null);
    }

    protected GameCardsStack() {
        initObject();
    }

    protected void setUpGameContent(GameContent content) {
        if(!isNotSetUpped())
            return;
        gameContent = content;
    }

    protected boolean push(GameCard card) {
        if(isNotSetUpped())
            return false;
        if (gameContent.containsCard(card)) {
            cardsOnStack.push(card);
            return true;
        }
        return false;
    }

    protected GameCard pop() {
        if(isNotSetUpped())
            return null;
        return cardsOnStack.pop();
    }

    protected GameCard getCardOnTop() {
        if(isNotSetUpped())
            return null;
        return cardsOnStack.peek();
    }

    protected int getStackSize() {
        if(isNotSetUpped())
            return -1;
        return cardsOnStack.size();
    }

    public abstract boolean pushCard(GameCard card);

    public abstract GameCard popCard();
}
