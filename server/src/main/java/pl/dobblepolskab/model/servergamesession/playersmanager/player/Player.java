package pl.dobblepolskab.model.servergamesession.playersmanager.player;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.PlayerStack;

public abstract class Player {
    private String name;
    private String clientId;
    private PlayerStack cardsStack;

    private void initObject(){
        cardsStack = new PlayerStack();
    }

    public Player(String name, String clientId){
        initObject();
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
}
