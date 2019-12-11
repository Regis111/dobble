package pl.dobblepolskab.model.servergamesession;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.server.Server;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.GameMainStack;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;

import java.util.Collections;
import java.util.LinkedList;

public class ServerGameSession {
    GameContent gameContent;
    GameMainStack mainStack;
    PlayersManager playersManager;
    ServerSocket serverSocket;

    private void initObject(){
    }

    public ServerGameSession(GameContent gameContent, ServerSocket serverSocket){
        initObject();
        this.gameContent = gameContent;
        this.serverSocket = serverSocket;
        mainStack = new GameMainStack(gameContent);
        playersManager = new PlayersManager();
    }

    public void startGameSession(){
        LinkedList<GameCard> cardsToGiveOut = gameContent.getCards();
        Collections.shuffle(cardsToGiveOut);
        playersManager.preparePlayersToGame(cardsToGiveOut);
        mainStack.initMainStack(cardsToGiveOut);
    }

}
