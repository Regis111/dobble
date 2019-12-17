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
    private GameContent gameContent;
    private GameMainStack mainStack;
    private PlayersManager playersManager;
    private ServerSocket serverSocket;

    public ServerGameSession(GameContent gameContent, ServerSocket serverSocket){
        this.gameContent = gameContent;
        this.serverSocket = serverSocket;
        mainStack = new GameMainStack(gameContent);
        playersManager = new PlayersManager(gameContent);
    }

    public void startGameSession(){
        LinkedList<GameCard> cardsToGiveOut = new LinkedList<>(gameContent.getCards());
        Collections.shuffle(cardsToGiveOut);
        playersManager.preparePlayersToGame(cardsToGiveOut);
        mainStack.initMainStack(cardsToGiveOut);
    }

}
