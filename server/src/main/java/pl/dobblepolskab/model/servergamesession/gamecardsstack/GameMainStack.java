package pl.dobblepolskab.model.servergamesession.gamecardsstack;

import gamecontent.GameCard;
import gamecontent.GameContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
@Scope(value = "singleton")
public class GameMainStack extends GameCardsStack {

    @Autowired
    public GameMainStack() {
        super(GameContent.getInstance());
        initObject();
    }

    public GameMainStack(GameContent gameContent, List<GameCard> cardsToAdd) {
        super(gameContent);
        initObject();
        initMainStack(cardsToAdd);
    }

    private void initObject() {
    }

    public boolean initMainStack(List<GameCard> cardsToAdd) {
        while(!isAllCardsPopped())
            pop();
        for (GameCard curCard : cardsToAdd)
            if (!push(curCard))
                return false;
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
