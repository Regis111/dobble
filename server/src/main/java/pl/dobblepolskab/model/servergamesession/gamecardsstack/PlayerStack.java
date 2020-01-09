package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import gamecontent.GameCard;
import gamecontent.GameContent;

public class PlayerStack extends GameCardsStack {
    private boolean initiated;

    public PlayerStack(GameContent gameContent) {
        super(gameContent);
        initObject();
    }

    public PlayerStack(GameContent gameContent, GameCard firstCard) {
        super(gameContent);
        initObject();
        initPlayerStack(firstCard);
    }

    private void initObject() {
        initiated = false;
    }

    public boolean initPlayerStack(GameCard firstCard) {
        if(initiated)
            return false;
        if (!push(firstCard))
            return false;
        initiated = true;
        return true;
    }

    @Override
    public boolean pushCard(GameCard card) {
        return push(card);
    }

    @Override
    public GameCard popCard() {
        return null;
    }

    public GameCard getTopCard(){
        return getCardOnTop();
    }

    public int getCardsCount(){
        return getStackSize();
    }
}
