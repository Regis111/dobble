package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import gamecontent.GameCard;
import gamecontent.GameContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class PlayerStack extends GameCardsStack {
    private boolean initiated;

    @Autowired
    public PlayerStack() {
        super(GameContent.getInstance());
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
