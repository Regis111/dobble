package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import gamecontent.GameCard;
import gamecontent.GameContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.PlayerStack;

@Component
@Scope(value = "prototype")
public abstract class Player {
    private String name;
    private String clientId;
    @Autowired
    private PlayerStack cardsStack;

    public Player(GameContent gameContent, String name, String clientId){
        this.name = name;
        this.clientId = clientId;
    }

    public void preparePlayerToGame(GameCard firstCard){
        cardsStack.initPlayerStack(firstCard);
    }

    public String getName() {
        return name;
    }

    public String getClientId() {
        return clientId;
    }

    public void pushCardOnStack(GameCard card){
        cardsStack.pushCard(card);
    }

    public int getPoints(){
        return cardsStack.getCardsCount();
    }

    public GameCard getTopCard(){
        return cardsStack.getTopCard();
    }
}
