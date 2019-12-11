package pl.dobblepolskab.model.servergamesession;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.model.server.Server;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.GameMainStack;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;

import java.util.LinkedList;

public class ServerGameSession {
    GameContent gameContent;
    GameMainStack mainStack;
    PlayersManager playersManager;

    private void initObject(){
    }

    public ServerGameSession(GameContent gameContent){
        initObject();
        this.gameContent = gameContent;
        mainStack = new GameMainStack(gameContent);
        playersManager = new PlayersManager();
    }

    public void startGameSession(){
        LinkedList<GameCard> cardsToGiveOut = gameContent.getCards();
        playersManager.preparePlayersToGame(cardsToGiveOut);
    }
}
