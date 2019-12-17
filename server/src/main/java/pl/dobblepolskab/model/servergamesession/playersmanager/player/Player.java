package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.PlayerStack;

public abstract class Player {
    private String name;
    private String clientId;
    private PlayerStack cardsStack;

    public Player(GameContent gameContent, String name, String clientId){
        this.name = name;
        this.clientId = clientId;
        cardsStack = new PlayerStack(gameContent);
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
}
