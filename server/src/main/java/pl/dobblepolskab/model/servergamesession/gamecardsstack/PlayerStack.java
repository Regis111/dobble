package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;

public class PlayerStack extends GameCardsStack {
    private boolean initiated;

    private void initObject() {
        initiated = false;
    }

    public PlayerStack() {
        super();
    }

    public PlayerStack(GameCard firstCard) {
        super();
        initPlayerStack(firstCard);
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
