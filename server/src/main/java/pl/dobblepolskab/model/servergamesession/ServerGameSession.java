package pl.dobblepolskab.model.servergamesession;

import pl.dobblepolskab.common.gamecontent.GameCard;
import pl.dobblepolskab.common.gamecontent.GameContent;
import pl.dobblepolskab.common.sockets.ServerSocket;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.GameMainStack;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;

import java.util.Collections;
import java.util.LinkedList;

public class ServerGameSession {
    private GameContent gameContent;
    private GameMainStack mainStack;
    private PlayersManager playersManager;
    private ServerSocket serverSocket;
    private boolean sessionRunning;
    private int shoutId;
    private boolean isCardTaken;

    public ServerGameSession(GameContent gameContent, ServerSocket serverSocket){
        this.gameContent = gameContent;
        this.serverSocket = serverSocket;
        mainStack = new GameMainStack(gameContent);
        playersManager = new PlayersManager(gameContent);
        sessionRunning = false;
        shoutId = 0;
        isCardTaken = false;
    }

    public boolean startGameSession(){
        if(sessionRunning)
            return false;
        LinkedList<GameCard> cardsToGiveOut = new LinkedList<>(gameContent.getCards());
        Collections.shuffle(cardsToGiveOut);
        playersManager.preparePlayersToGame(cardsToGiveOut);
        mainStack.initMainStack(cardsToGiveOut);
        shoutId = 1;
        sessionRunning = true;
        return true;
    }

    public boolean endGameSession(){
        if(!sessionRunning || mainStack.getCardsCount() > 0)
            return false;
        sessionRunning = false;
        return true;
    }

    public int[] getNextTopCardsForPlayer(String playerClientId){
        return new int[] {gameContent.getCardIdInModel(mainStack.getTopCard()),
                gameContent.getCardIdInModel(playersManager.getTopCardOfPlayer(playerClientId))};
    }

    public boolean startNextShout(){
        if(!isCardTaken)
            return false;
        shoutId++;
        isCardTaken = false;
        return true;
    }

    public int getShoutId() {
        return shoutId;
    }

    public boolean isWinner(String playerClientId, int requestShoutId){
        if(requestShoutId != shoutId || isCardTaken)
            return false;
        playersManager.pushGameCardOnPlayerStack(playerClientId, mainStack.popCard());
        isCardTaken = true;
        return true;
    }


}
