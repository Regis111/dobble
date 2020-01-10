package pl.dobblepolskab.model.servergamesession;

import gamecontent.GameCard;
import gamecontent.GameContent;
import messages.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.dobblepolskab.model.servergamesession.gamecardsstack.GameMainStack;
import pl.dobblepolskab.model.servergamesession.playersmanager.PlayersManager;
import pl.dobblepolskab.model.servergamesession.playersmanager.player.Player;
import pl.dobblepolskab.services.AdminPlayerService;
import pl.dobblepolskab.services.GameService;
import pl.dobblepolskab.services.SessionConfigurationService;

import java.util.Collections;
import java.util.LinkedList;

@Component
@Scope(value = "singleton")
public class ServerGameSession implements GameService, SessionConfigurationService {
    private GameContent gameContent;
    private GameMainStack mainStack;
    private PlayersManager playersManager;
    private boolean sessionRunning;
    private int shoutId;
    private boolean isCardTaken;

    @Autowired
    public ServerGameSession(GameContent gameContent, GameMainStack gameMainStack,
                             PlayersManager playersManager){
        this.gameContent = gameContent;
        mainStack = gameMainStack;
        this.playersManager = playersManager;
        sessionRunning = false;
        shoutId = 0;
        isCardTaken = false;
    }

    @Override
    public void startGameSession(){
        if(sessionRunning)
            return;
        LinkedList<GameCard> cardsToGiveOut = new LinkedList<>(gameContent.getCards());
        Collections.shuffle(cardsToGiveOut);
        playersManager.preparePlayersToGame(cardsToGiveOut);
        mainStack.initMainStack(cardsToGiveOut);
        shoutId = 1;
        sessionRunning = true;
    }

    public void endGameSession(){
        if(!sessionRunning || mainStack.getCardsCount() > 0)
            return;
        sessionRunning = false;
    }

    @Override
    public Pair getNextTurnState(String playerClientId){
        return new Pair(gameContent.getCardIdInModel(mainStack.getTopCard()),
                gameContent.getCardIdInModel(playersManager.getTopCardOfPlayer(playerClientId)));
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

    @Override
    public boolean isWinner(String playerClientId, int requestShoutId){
        if(requestShoutId != shoutId || isCardTaken)
            return false;
        playersManager.pushGameCardOnPlayerStack(playerClientId, mainStack.popCard());
        isCardTaken = true;
        return true;
    }

    @Override
    public void setComputerPlayersNumber(int playersNumber) {
        playersManager.setComputerPlayersNumber(playersNumber);
    }

    @Override
    public void setComputerDifficulty(int computerDifficulty) {
        playersManager.setComputerIntelligenceLevel(computerDifficulty);
    }
}
