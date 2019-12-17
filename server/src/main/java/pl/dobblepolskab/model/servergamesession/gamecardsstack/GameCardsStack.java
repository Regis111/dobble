package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;

import java.util.LinkedList;

public abstract class GameCardsStack {
    private GameContent gameContent;
    private LinkedList<GameCard> cardsOnStack;

    protected GameCardsStack(GameContent gameContent) {
        initObject();
        setUpGameContent(gameContent);
    }

    private void initObject() {
        cardsOnStack = new LinkedList<>();
    }

    private void setUpGameContent(GameContent content) {
        gameContent = content;
    }

    protected boolean push(GameCard card) {
        if (gameContent.containsCard(card)) {
            cardsOnStack.push(card);
            return true;
        }
        return false;
    }

    protected GameCard pop() {
        return cardsOnStack.pop();
    }

    protected GameCard getCardOnTop() {
        return cardsOnStack.peek();
    }

    protected int getStackSize() {
        return cardsOnStack.size();
    }

    public abstract boolean pushCard(GameCard card);

    public abstract GameCard popCard();
}
